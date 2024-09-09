package minionz.backend.chat.message.model.request;

import lombok.*;

@Getter
@Builder(toBuilder = true)
public class MessageRequest {

    private String messageContents;
//    private MultipartFile file;
    private String  file;
    private Long chatRoomId;
    private Long senderId;
}
