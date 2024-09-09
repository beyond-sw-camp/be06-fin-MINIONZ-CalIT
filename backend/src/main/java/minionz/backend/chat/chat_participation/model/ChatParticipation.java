package minionz.backend.chat.chat_participation.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.backend.chat.message.model.Message;
import minionz.backend.chat.chat_room.model.ChatRoom;
import minionz.backend.user.model.User;

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
    @OneToMany(mappedBy = "chatParticipation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> messages = new ArrayList<>();

    // User N : 1
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // ChatRoom N : 1
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "chatRoom_id")
    private ChatRoom chatRoom;

}
