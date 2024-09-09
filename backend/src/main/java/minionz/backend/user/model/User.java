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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String loginId;
    private String password;
    private String userName;
    private Integer loginType;
    private boolean isVeryfied;
    private String email;
    private String createdAt;
    private String userRole = "ROLE_ADMIN";


}
