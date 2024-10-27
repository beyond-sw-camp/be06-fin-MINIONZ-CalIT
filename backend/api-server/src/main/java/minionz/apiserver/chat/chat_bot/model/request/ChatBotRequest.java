package minionz.apiserver.chat.chat_bot.model.request;

import lombok.*;

@Getter
@Builder(toBuilder = true)
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatBotRequest {
    private String messageContents;
    private Long userId;
    private boolean isFromChatBot = false;
}
