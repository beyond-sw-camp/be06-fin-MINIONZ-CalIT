package minionz.apiserver.chat.message;

import lombok.RequiredArgsConstructor;
import minionz.apiserver.chat.chat_room.model.response.FileInfoResponse;
import minionz.apiserver.chat.chat_room.model.response.ReadMessageResponse;
import minionz.apiserver.chat.message.model.request.SendMessageRequest;
import minionz.apiserver.chat.message.model.request.UpdateMessageRequest;
import minionz.apiserver.common.exception.BaseException;
import minionz.apiserver.common.responses.BaseResponse;
import minionz.apiserver.common.responses.BaseResponseStatus;
import minionz.apiserver.user.model.CustomSecurityUserDetails;
import minionz.common.user.model.User;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessageController {

  private final MessageService messageService;

  // 메세지 전송
  @MessageMapping("/room/{chatRoomId}/send")
  public void sendMessage(@Payload SendMessageRequest request) {
    Long senderId = request.getUserId();
    messageService.sendMessage(request.getChatRoomId(), request, null, senderId);
  }

  // 파일 전송
  @PostMapping("/message/sendFile")
  public void sendFile(
          @RequestParam("chatRoomId") Long chatRoomId,
          @RequestPart(name = "files") MultipartFile[] files,
          @AuthenticationPrincipal CustomSecurityUserDetails customUserDetails) {

    Long senderId = customUserDetails.getUser().getUserId();
    SendMessageRequest request = new SendMessageRequest();
    request.setChatRoomId(chatRoomId);
    request.setUserId(customUserDetails.getUserId());
    request.setUserName(customUserDetails.getUserName());
    // 파일 처리
    messageService.sendMessage(chatRoomId, request, files, senderId);
  }

  // 채팅 내역 조회 : 페이징처리
  @GetMapping(value = "/message/{chatRoomId}")
  public BaseResponse<List<ReadMessageResponse>> readMessage(
          @AuthenticationPrincipal CustomSecurityUserDetails customUserDetails, @PathVariable Long chatRoomId,
          @RequestParam Integer page, @RequestParam Integer size) {
    try {
      User user = customUserDetails.getUser();
      List<ReadMessageResponse> response = messageService.readMessage(chatRoomId, user.getUserId(), page, size);
      return new BaseResponse<>(BaseResponseStatus.CHAT_HISTORY_RETRIEVAL_SUCCESS, response);
    } catch (BaseException e) {
      return new BaseResponse<>(e.getStatus());
    }
  }

  // 파일 내역 조회
  @GetMapping(value = "/message/{chatRoomId}/files")
  public BaseResponse<List<FileInfoResponse>> getFileMessages(
          @PathVariable Long chatRoomId) {
    try {
      List<FileInfoResponse> response = messageService.getFileList(chatRoomId);
      return new BaseResponse<>(BaseResponseStatus.FILE_RETRIEVAL_SUCCESS, response);
    } catch (BaseException e) {
      return new BaseResponse<>(e.getStatus());
    }
  }

  // 메세지 수정
  @MessageMapping("/room/{chatRoomId}/edit")
  public void updateMessage(@Payload UpdateMessageRequest request,
                            @AuthenticationPrincipal CustomSecurityUserDetails customUserDetails) {
    Long senderId = customUserDetails.getUser().getUserId();
    messageService.updateMessage(request.getChatRoomId(), request, senderId);
  }

  // 메세지 삭제
  @DeleteMapping("/message/{messageId}")
  public BaseResponse<BaseResponseStatus> deleteMessage(@PathVariable Long messageId,
                                                        @AuthenticationPrincipal CustomSecurityUserDetails customUserDetails) {
    try {
      Long senderId = customUserDetails.getUser().getUserId();
      messageService.deleteMessage(messageId, senderId);
      return new BaseResponse<>(BaseResponseStatus.MESSAGE_DELETE_SUCCESS);
    } catch (BaseException e) {
      return new BaseResponse<>(e.getStatus());
    }

  }

  // 상태 업데이트
  @GetMapping("/enter/{chatRoomId}")
  public BaseResponse<BaseResponseStatus> enterChatRoom(@PathVariable Long chatRoomId,
                                                        @AuthenticationPrincipal CustomSecurityUserDetails userDetails) {
    try {
      Long userId = userDetails.getUser().getUserId();
      messageService.enterChatRoom(chatRoomId, userId);
      return new BaseResponse<>(BaseResponseStatus.MESSAGE_STATUS_UPDATE_SUCCESS);
    } catch (BaseException e) {
      return new BaseResponse<>(e.getStatus());
    }

  }
}

