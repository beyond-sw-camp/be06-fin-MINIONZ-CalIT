package minionz.backend.chat.message.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.backend.chat.chat_participation.model.ChatParticipation;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    private Long chatRoomId;
    private Long userId;

    private String content;
    private String fileType;
    private String fileSize;
    private String fileUrl;
    private String fileName;
    private LocalDateTime deletedAt;
    private MessageType messageType;
    private MessageStatus messageStatus;

    // ChatParticipation N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatParticipation_id")
    private ChatParticipation chatParticipation;

}
