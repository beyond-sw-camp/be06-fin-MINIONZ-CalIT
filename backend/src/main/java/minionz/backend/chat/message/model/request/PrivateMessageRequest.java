package minionz.backend.chat.message.model.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class PrivateMessageRequest {
    private String messageContents;
    //    private MultipartFile file;
    private String  file;
    private Long chatRoomId;
    private Long senderId;
    private Long receiverId;
}
