package minionz.apiserver.user.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

//회원 가입 시 입력 받는 객체
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequest {
    @NotBlank(message = "ID를 입력하세요.")
    private String loginId;
    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;
    private String passwordCheck;
    @NotBlank(message = "이메일을 입력하세요.")
    private String email;
    @NotBlank(message = "이름을 입력하세요.")
    private String userName;
}