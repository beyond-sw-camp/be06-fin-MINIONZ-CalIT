package minionz.backend.chat.message;

import lombok.RequiredArgsConstructor;
import minionz.backend.chat.message.model.request.MessageRequest;
import minionz.backend.user.model.CustomSecurityUserDetails;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @MessageMapping("/room/{chatRoomId}/sendFile")
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
}

