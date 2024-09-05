package minionz.backend.chat.chat_room.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.backend.chat.chat_participation.model.ChatParticipation;
import minionz.backend.common.BaseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ChatRoom extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;

    private String chatRoomName;
    private LocalDateTime deletedAt;
    private String fileType;
    private String fileSize;
    private String fileUrl;
    private String fileName;

    // ChatParticipation 1 : N
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChatParticipation> chatParticipations = new ArrayList<>();

}
