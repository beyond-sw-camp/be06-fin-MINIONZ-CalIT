package minionz.backend.chat.message.model.request;

import lombok.*;

import java.util.List;

@Getter
@Builder(toBuilder = true)
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {

    private String messageContents;
//    private MultipartFile file;
    private List<String> files;
    private Long chatRoomId;
}
