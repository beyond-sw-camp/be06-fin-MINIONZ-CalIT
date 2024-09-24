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
    private Long id;

    private String loginId;
    private String password;
    private String email;
    private String userName;
    private String userRole = "ROLE_USER";
    private Integer loginType;
    private boolean isEnabled = false;
    private String createdAt;
    private String verificationCode = null;
    private UserRole role;
    private String provider; // google
    private String providerId; // google 유저 고유 ID
}


