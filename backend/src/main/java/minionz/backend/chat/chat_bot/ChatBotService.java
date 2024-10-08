package minionz.backend.chat.chat_bot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import minionz.backend.chat.chat_bot.model.ChatBot;
import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.user.UserRepository;
import minionz.backend.user.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatBotService {

    private final WebClient.Builder webClientBuilder;
    private final UserRepository userRepository;
    private final ChatBotRepository chatBotRepository;
    private final ObjectMapper objectMapper;

    @Value("${gemini.api-key}")
    private String apiKey;

    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent";

    // GPT에 질문을 보내고, 응답을 저장하는 메서드
    public Mono<String> askAi(String question, Long userId) {
        // User 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NOT_FOUND));

        // 원래 DB에서 뭘 읽어와서 하려고 했는데 DB에서 읽어온 데이터를 형식으로 넣어주기?

        String requestBody = """
                {
                  "contents": [
                    {
                      "parts": [
                        {
                          "text": "%s"
                        }
                      ]
                    }
                  ]
                }
                """.formatted(question);

        return webClientBuilder.build()
                .post()
                .uri(GEMINI_API_URL + "?key=" + apiKey)  // Append API key to the URL
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    // JSON에서 "text" 값 추출
                    String responseText = extractTextFromResponse(response);
                    // ChatBot 대화 저장
                    saveChatBotConversation(question, responseText, user);
                    return responseText;
                });
    }

    // JSON 응답에서 "text" 값만 추출하는 메서드
    private String extractTextFromResponse(String response) {
        try {
            JsonNode root = objectMapper.readTree(response);
            return root.path("candidates").get(0)
                    .path("content").path("parts").get(0)
                    .path("text").asText();
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse response", e);
        }
    }

    // ChatBot 대화를 저장
    private void saveChatBotConversation(String question, String response, User user) {
        ChatBot chatBot = ChatBot.builder()
                .user(user)
                .question(question)
                .response(response)
                .timestamp(LocalDateTime.now())
                .build();
        chatBotRepository.save(chatBot);
    }
}

