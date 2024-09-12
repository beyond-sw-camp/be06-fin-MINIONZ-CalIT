package minionz.backend.chat.message;

import lombok.RequiredArgsConstructor;
import minionz.backend.chat.chat_room.model.response.ReadMessageResponse;
import minionz.backend.chat.message.model.request.MessageRequest;
import minionz.backend.common.responses.BaseResponse;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.user.model.CustomSecurityUserDetails;
import minionz.backend.user.model.User;
import org.springframework.messaging.handler.annotation.MessageMapping;
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
    public void sendMessage(MessageRequest request, @AuthenticationPrincipal CustomSecurityUserDetails userDetails) {
        Long senderId = userDetails.getUser().getUserId();
        messageService.sendMessage(request.getChatRoomId(), request, null, senderId);
    }

    // 파일 전송
    @PostMapping("/message/sendFile")
    public void sendFile(
            @RequestParam("chatRoomId") Long chatRoomId,
            @RequestPart(name = "files") MultipartFile[] files,
            @AuthenticationPrincipal CustomSecurityUserDetails userDetails) {

        Long senderId = userDetails.getUser().getUserId();
        MessageRequest request = new MessageRequest();
        request.setChatRoomId(chatRoomId);
        // 파일 처리
        messageService.sendMessage(chatRoomId, request, files, senderId);
    }

    @GetMapping(value = "/message/{chatRoomId}")
    public BaseResponse<List<ReadMessageResponse>> readMessage(@AuthenticationPrincipal CustomSecurityUserDetails customUserDetails, @PathVariable Long chatRoomId) {
        User user = customUserDetails.getUser();
        List<ReadMessageResponse> response = messageService.readMessage(chatRoomId, user.getUserId());
        return new BaseResponse<>(BaseResponseStatus.CHAT_HISTORY_RETRIEVAL_SUCCESS, response);
    }
}

