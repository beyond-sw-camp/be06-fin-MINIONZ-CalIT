package minionz.apiserver.chat.chat_bot;

import lombok.RequiredArgsConstructor;
import minionz.apiserver.chat.chat_bot.model.request.ChatBotRequest;
import minionz.apiserver.common.exception.BaseException;
import minionz.apiserver.common.responses.BaseResponse;
import minionz.apiserver.common.responses.BaseResponseStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ChatBotController {

    private final ChatBotService chatBotService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/bot/message")
    public BaseResponse<String> chatBotMessage(@Payload ChatBotRequest request) {
        try {
            if (!request.isFromChatBot()) {
                Long botQuestionId = chatBotService.saveUserMessage(request);
                chatBotService.sendMessageToN8n(botQuestionId, request.getMessageContents(), request.getUserId());
                return new BaseResponse<>(BaseResponseStatus.CHATBOT_MESSAGE_RECEIVED);
            }
            return new BaseResponse<>(BaseResponseStatus.CHATBOT_RESPONSE_SAVED);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    // n8n에서 받은 응답
    @PostMapping("/receiveFromN8n")
    public BaseResponse<String> receiveFromN8n(@RequestBody Map<String, String> requestData) {
        try {
            String message = requestData.get("message");
//            Long userId = Long.parseLong(requestData.get("userId"));
            Long botQuestionId = Long.parseLong(requestData.get("botQuestionId"));
            Long userId = chatBotService.saveChatBotResponse(message, botQuestionId);
            messagingTemplate.convertAndSend("/subUser/" + userId, message);
            return new BaseResponse<>(BaseResponseStatus.CHATBOT_RESPONSE_SAVED);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
