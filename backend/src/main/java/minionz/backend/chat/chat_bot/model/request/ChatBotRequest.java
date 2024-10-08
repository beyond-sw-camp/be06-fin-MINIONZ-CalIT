package minionz.backend.chat.chat_bot.model.request;

import lombok.*;

@Getter
@Builder(toBuilder = true)
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatBotRequest {
    private String question;
    private Long userId;
}
