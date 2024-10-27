package minionz.common.chat.chat_bot.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.common.common.BaseEntity;
import minionz.common.user.model.User;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ChatBot extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long botQuestionId;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String question;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String response;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
