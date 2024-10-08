package minionz.apiserver.chat.message.model.request;

import lombok.*;

import java.util.List;

@Getter
@Builder(toBuilder = true)
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendMessageRequest {

    private String messageContents;
    private List<String> files;
    private Long chatRoomId;
    private Long userId;
    private String userName;
}
