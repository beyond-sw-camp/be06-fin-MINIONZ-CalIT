package minionz.apiserver.chat.chat_room;

import lombok.RequiredArgsConstructor;
import minionz.apiserver.chat.chat_room.model.request.CreateChatRoomRequest;
import minionz.apiserver.chat.chat_room.model.request.SearchChatRoomRequest;
import minionz.apiserver.chat.chat_room.model.request.UpdateChatRoomRequest;
import minionz.apiserver.chat.chat_room.model.response.CreateChatRoomResponse;
import minionz.apiserver.chat.chat_room.model.response.ReadChatRoomResponse;
import minionz.apiserver.chat.chat_room.model.response.SearchChatRoomResponse;
import minionz.apiserver.common.exception.BaseException;
import minionz.apiserver.common.responses.BaseResponse;
import minionz.apiserver.common.responses.BaseResponseStatus;
import minionz.apiserver.user.model.CustomSecurityUserDetails;
import minionz.common.user.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatRoomController {
  private final ChatRoomService chatRoomService;

  @PostMapping(value = "/room")
  public BaseResponse<CreateChatRoomResponse> create(
      @AuthenticationPrincipal CustomSecurityUserDetails customUserDetails,
      @RequestBody CreateChatRoomRequest request) {
    try {
      User user = customUserDetails.getUser();
      CreateChatRoomResponse response = chatRoomService.create(user, request).getResult();
      return new BaseResponse<>(BaseResponseStatus.CHATROOM_CREATE_SUCCESS, response);
    } catch (BaseException e) {
      return new BaseResponse<>(e.getStatus());
    }

  }

  @GetMapping(value = "/{workspaceId}/roomList")
  public BaseResponse<List<ReadChatRoomResponse>> readRoomList(@PathVariable Long workspaceId,
      @AuthenticationPrincipal CustomSecurityUserDetails customUserDetails) {
    try {
      User user = customUserDetails.getUser();
      List<ReadChatRoomResponse> response = chatRoomService.roomList(user.getUserId(), workspaceId);
      return new BaseResponse<>(BaseResponseStatus.CHATROOM_LIST_SUCCESS, response);
    } catch (BaseException e) {
      return new BaseResponse<>(e.getStatus());
    }

  }

  @PatchMapping(value = "/room/{chatRoomId}/edit")
  public BaseResponse<BaseResponseStatus> updateRoomName(@PathVariable Long chatRoomId,
      @RequestBody UpdateChatRoomRequest request,
      @AuthenticationPrincipal CustomSecurityUserDetails customUserDetails) {
    try {
      User user = customUserDetails.getUser();
      BaseResponse<BaseResponseStatus> response = chatRoomService.updateChatRoomName(chatRoomId, request, user);
      return response;
    } catch (BaseException e) {
      return new BaseResponse<>(e.getStatus());
    }

  }

  @DeleteMapping("/{chatRoomId}/exit")
  public BaseResponse<BaseResponseStatus> exitChatRoom(
      @AuthenticationPrincipal CustomSecurityUserDetails customUserDetails,
      @PathVariable Long chatRoomId) {
    try {
      User user = customUserDetails.getUser();
      BaseResponse<BaseResponseStatus> response = chatRoomService.exitChatRoom(chatRoomId, user);
      return response;
    } catch (BaseException e) {
      return new BaseResponse<>(e.getStatus());
    }

  }

  @GetMapping("/search")
  public BaseResponse<List<SearchChatRoomResponse>> searchChatRooms(@RequestBody SearchChatRoomRequest request) {
    try {
      List<SearchChatRoomResponse> responses = chatRoomService.searchRoomList(request);
      return new BaseResponse<>(BaseResponseStatus.CHATROOM_SEARCH_SUCCESS, responses);
    } catch (BaseException e) {
      return new BaseResponse<>(e.getStatus());
    }
  }

}
