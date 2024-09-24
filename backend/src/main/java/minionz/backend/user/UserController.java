package minionz.backend.user;

import minionz.backend.utils.JwtUtil;
import minionz.backend.user.model.JwtResponse;
import minionz.backend.user.model.User;
import minionz.backend.user.model.request.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signupUser(@RequestBody CreateUserRequest createUserRequest) {
        System.out.println(createUserRequest);
        userService.signupUser(createUserRequest);
        return ResponseEntity.ok("회원 가입이 완료 되었습니다!");
    }

    @PostMapping("/check-id")
    public boolean checkUserId(@RequestBody CheckIdRequest checkIdRequest) {
        boolean duplicate = userService.checkLoginDuplicate(checkIdRequest.getLoginId());
        if(!duplicate) {
            return true;
        } else {
            return false;
        }
    }

    @PostMapping("/send-verification-code")
    public boolean sendVerificationCode(@RequestBody EmailVerifyRequest emailVerifyRequest) {
        String email = emailVerifyRequest.getEmail();
        System.out.println(email + "여기까지 옴");
        boolean duplicate = userService.sendVerificationCode(email); //중복이면 duplicate가 true
        if (!duplicate) {
            return false;
        } else {
            return true;
        }
    }

    @PostMapping("/confirm-verification-code")
    public ResponseEntity<String> confirmVerificationCode(@RequestBody UuidRequest uuidRequest) {
        boolean verify = userService.verifyUser(uuidRequest.getUuid());
        if (verify) {
            return ResponseEntity.ok("인증 되었습니다!");
        }
        return ResponseEntity.ok("이메일 및 인증코드를 확인해주세요!");
    }
}