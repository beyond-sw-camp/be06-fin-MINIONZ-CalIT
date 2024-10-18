package minionz.apiserver.chat.chat_bot.model.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ReadChatBotResponse {
    private Long botQuestionId;
    private String question;
    private String response;
    private LocalDateTime userQuestionAt;
    private LocalDateTime botResponseAt;
    private Long userId;
    private String userName;
}
