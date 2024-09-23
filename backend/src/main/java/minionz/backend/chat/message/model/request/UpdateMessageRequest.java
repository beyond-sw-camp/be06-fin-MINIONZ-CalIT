package minionz.backend.chat.message.model.request;

import lombok.*;

@Getter
@Builder(toBuilder = true)
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMessageRequest {
    private Long chatRoomId;
    private Long messageId;
    private String userName;
    private String messageContents;

}
