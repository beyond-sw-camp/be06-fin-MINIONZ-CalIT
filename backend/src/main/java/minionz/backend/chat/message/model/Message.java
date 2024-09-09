package minionz.backend.chat.message.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.backend.chat.chat_participation.model.ChatParticipation;
import minionz.backend.common.BaseEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Message extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    private Long chatRoomId;
    private Long userId;

    private String messageContents;
    private String fileType;
    private String fileSize;
    private String fileUrl;
    private String fileName;
    private LocalDateTime deletedAt;

    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    @Enumerated(EnumType.STRING)
    private MessageStatus messageStatus;

    // ChatParticipation N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_participation_id")
    private ChatParticipation chatParticipation;

}
