package minionz.backend.chat.chat_bot;

import lombok.RequiredArgsConstructor;
import minionz.backend.chat.chat_bot.model.request.ChatBotRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ChatBotController {

    private final ChatBotService chatbotService;

    // GPT 로 질문을 보내고 응답을 받는 경로
    @PostMapping("/chatbot/ask-ai")
    public Mono<String> askAi(@RequestBody ChatBotRequest request) {
        return chatbotService.askAi(request.getQuestion(), request.getUserId());
    }
}
