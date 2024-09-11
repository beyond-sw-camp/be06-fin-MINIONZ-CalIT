package minionz.backend.chat.message;

import lombok.RequiredArgsConstructor;
import minionz.backend.chat.message.model.request.MessageRequest;
import minionz.backend.chat.message.model.response.MessageResponse;
import minionz.backend.common.responses.BaseResponse;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.user.model.CustomSecurityUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    // 그룹 메세지 전송
    @PostMapping("/send")
    public BaseResponse<MessageResponse> sendMessage(@RequestBody MessageRequest request, @AuthenticationPrincipal CustomSecurityUserDetails userDetails) {
        Long senderId = userDetails.getUser().getUserId();
        MessageResponse response = messageService.sendMessage(request.getChatRoomId(), request,null, senderId);
        return new BaseResponse<>(BaseResponseStatus.MESSAGE_SEND_SUCCESS, response);
    }

    @PostMapping("/sendFile")
    public BaseResponse<MessageResponse> sendFile(
            @RequestParam("chatRoomId") Long chatRoomId,
            @RequestPart(name = "files") MultipartFile[] files,
            @AuthenticationPrincipal CustomSecurityUserDetails userDetails) {

        Long senderId = userDetails.getUser().getUserId();
        MessageRequest request = new MessageRequest();
        request.setChatRoomId(chatRoomId);
        // files 전달
        MessageResponse response = messageService.sendMessage(chatRoomId, request, files, senderId);

        return new BaseResponse<>(BaseResponseStatus.MESSAGE_SEND_SUCCESS, response);
    }

}

