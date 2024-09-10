package minionz.backend.chat.message;

import lombok.RequiredArgsConstructor;
import minionz.backend.chat.message.model.request.MessageRequest;
import minionz.backend.chat.message.model.response.MessageResponse;
import minionz.backend.common.responses.BaseResponse;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.user.model.CustomSecurityUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    // 그룹 메세지 전송
    @PostMapping("/send")
    public BaseResponse<MessageResponse> sendMessage(@RequestBody MessageRequest request, @AuthenticationPrincipal CustomSecurityUserDetails userDetails) {
        Long senderId = userDetails.getUser().getUserId();
        System.out.println("Sender ID: " + senderId);
        MessageResponse response = messageService.sendMessage(request.getChatRoomId(), request, senderId);

        return new BaseResponse<>(BaseResponseStatus.MESSAGE_SEND_SUCCESS, response);
    }

}

