package minionz.apiserver.chat.chat_bot;

import lombok.RequiredArgsConstructor;
import minionz.apiserver.chat.chat_bot.model.request.ChatBotRequest;
import minionz.apiserver.chat.chat_bot.model.response.ReadChatBotResponse;
import minionz.apiserver.common.exception.BaseException;
import minionz.apiserver.common.responses.BaseResponseStatus;
import minionz.common.chat.chat_bot.ChatBotRepository;
import minionz.common.chat.chat_bot.model.ChatBot;
import minionz.common.user.UserRepository;
import minionz.common.user.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ChatBotService {

    private final ChatBotRepository chatBotRepository;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    // 사용자 메시지를 DB에 저장
    public Long saveUserMessage(ChatBotRequest request) {
        try {
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.CHATBOT_USER_NOT_FOUND));

            ChatBot chatBotMessage = ChatBot.builder()
                    .question(request.getMessageContents())  // 사용자 메시지 저장
                    .user(user)
                    .build();

            chatBotMessage = chatBotRepository.save(chatBotMessage);
            return chatBotMessage.getBotQuestionId();
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.CHATBOT_DATABASE_ERROR);
        }
    }

    // n8n으로 메시지를 전송하고 응답 받기
    public String sendMessageToN8n(Long botQuestionId, String message, Long userId) {
        String webhookUrl;
        if (message.contains("회의록")) {
            // 회의록 요약에 대한 요청
            webhookUrl = "http://localhost:5678/webhook/calit-note";
        } else if (message.contains("회의")) {
            // 일반 회의에 대한 요청
            webhookUrl = "http://localhost:5678/webhook/calit-meeting";
        } else {
            // 기타 메시지에 대한 처리
            webhookUrl = "http://localhost:5678/webhook/calit-etc";
        }

        try {
            Map<String, String> jsonMap = new HashMap<>();
            jsonMap.put("message", message);
            jsonMap.put("userId", String.valueOf(userId));
            jsonMap.put("botQuestionId", String.valueOf(botQuestionId));
            // n8n로 post -> webhook 동작
            return restTemplate.postForObject(webhookUrl, jsonMap, String.class);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.CHATBOT_EXTERNAL_API_ERROR);
        }
    }

    // 챗봇 응답을 DB에 저장
    public Long saveChatBotResponse(String message, Long botQuestionId) {
        try {
            ChatBot chatBotResponse = chatBotRepository.findById(botQuestionId).orElseThrow();
            chatBotResponse.setResponse(message);
            chatBotRepository.save(chatBotResponse);
            return chatBotResponse.getUser().getUserId();
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.CHATBOT_DATABASE_ERROR);
        }
    }

    public List<ReadChatBotResponse> getChatHistory (Long userId) {
        try{
            List<ChatBot> chatHistory = chatBotRepository.findAllByUser_UserIdOrderByCreatedAtAsc(userId);

            return chatHistory.stream()
                    .map(chatBot -> ReadChatBotResponse.builder()
                            .botQuestionId(chatBot.getBotQuestionId())
                            .question(chatBot.getQuestion())
                            .response(chatBot.getResponse())
                            .userQuestionAt(chatBot.getCreatedAt())
                            .botResponseAt(chatBot.getModifiedAt())
                            .userId(chatBot.getUser().getUserId())
                            .userName(chatBot.getUser().getUserName())
                            .build())
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.CHATBOT_LIST_FAILED);
        }
    }
}
