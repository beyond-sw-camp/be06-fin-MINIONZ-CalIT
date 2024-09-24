package minionz.backend.user;

import minionz.backend.Utils.JwtUtil;
import minionz.backend.user.model.User;
import minionz.backend.user.model.request.CreateUserRequest;
import minionz.backend.user.model.request.LoginUserRequest;
import minionz.backend.user.model.request.VerificationCodeRequest;
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
        userService.signupUser(createUserRequest);
        return ResponseEntity.ok("Verification email sent! Please check your email.");

    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestBody VerificationCodeRequest verificationCodeRequest) {
        boolean verified = userService.verifyUser(verificationCodeRequest.getCode());

        if (verified) {
            return ResponseEntity.ok("Account verified! You can now login.");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired verification code.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginUserRequest loginUserRequest) {
        User user = userService.loginUser(loginUserRequest);

        // JWT 토큰 생성
        String token = jwtUtil.createToken(user.getLoginId(), user.getUserName(), user.getUserRole(), user.getEmail());

        // 로그인 성공 시 JWT 토큰을 반환
        return ResponseEntity.ok(token);
    }
}