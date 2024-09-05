package minionz.backend.user.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.backend.chat_participation.model.ChatParticipation;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String Id;
    private String password;
    private String userName;
    private Integer loginType;
    private boolean isVeryfied;
    private String email;
    private String createdAt;
    private String userRole;

    // ChatParticipation 1 : N
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChatParticipation> chatParticipations = new ArrayList<>();


}
