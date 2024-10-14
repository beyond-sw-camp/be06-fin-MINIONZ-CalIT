package minionz.common.chat.chat_participation.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.common.chat.chat_room.model.ChatRoom;
import minionz.common.chat.message.model.Message;
import minionz.common.user.model.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ChatParticipation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatParticipationId;

    // Message 1 : N
    @OneToMany(mappedBy = "chatParticipation", fetch = FetchType.LAZY)
    private List<Message> messages = new ArrayList<>();

    // User N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // ChatRoom N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    // 사용자가 참여 중인 상태
    @Column(nullable = false)
    private boolean isActive = true;

}
