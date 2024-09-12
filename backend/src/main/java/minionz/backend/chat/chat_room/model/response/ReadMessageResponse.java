package minionz.backend.chat.chat_room.model.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import minionz.backend.chat.message.model.MessageStatus;
import minionz.backend.chat.message.model.MessageType;

import java.time.LocalDateTime;

@Builder
@Getter
public class ReadMessageResponse {
    private Long messageId;
    private Long senderId;

    private String userName;
    private String messageContents;
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    @Enumerated(EnumType.STRING)
    private MessageStatus messageStatus;

    private String fileType;
    private String fileSize;
    private String fileUrl;
    private String fileName;

    private Integer persona; // 프로필 이미지

}
