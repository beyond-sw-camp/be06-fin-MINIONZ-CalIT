package minionz.backend.user.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true)
    private String loginId;
    private String password;
    @Column(unique = true)
    private String email;
    private String userName;
    private Integer loginType;
    private boolean isEnabled = false;
    private String createdAt;
    private String role = "USER";
    private String provider; // google
    private String providerId; // google 유저 고유 ID
    private Integer persona;
}


