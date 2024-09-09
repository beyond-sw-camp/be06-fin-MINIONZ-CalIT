package minionz.backend.chat.message.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest {

    private Long senderId;
    private Long chatroomId;
    private String messageContents;
    private MultipartFile file;

}
