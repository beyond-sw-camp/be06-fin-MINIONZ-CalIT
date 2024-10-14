package minionz.apiserver.user.model.request;

import lombok.*;

//로그인 시 입력 받는 객체
@Getter
@Setter
@NoArgsConstructor
public class LoginUserRequest {
    private String loginId;
    private String password;
}
