package minionz.backend.user.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserResponse {
    private String id;
    private String password;
    private String email;
    private String userName;
    private Boolean isVerified;
}