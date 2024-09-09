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

    @GetMapping("/check-id")
    public ResponseEntity<String> checkUserId(@RequestParam String loginId) {
        boolean duplicate = userService.checkLoginDuplicate(loginId);
        if(duplicate) {
            return ResponseEntity.ok("사용 가능한 아이디 입니다!");
        } else {
        return ResponseEntity.ok("중복된 아이디 입니다.");
        }
    }

    @PostMapping("/send-verificationcode")
    public ResponseEntity<String> sendVerificationCode(@RequestBody EmailVerifyRequest emailVerifyRequest) {
        String email = emailVerifyRequest.getEmail();
        boolean duplicate = userService.sendVerificationCode(email);
        if (duplicate) {
            return ResponseEntity.ok("인증 메일이 발송 되었습니다!");
        } else {
            return ResponseEntity.ok("이미 가입된 이메일 입니다!");
        }
    }

    @PostMapping("/confirm-verificationcode")
    public ResponseEntity<String> confirmVerificationCode(@RequestBody UuidRequest uuidRequest) {
        boolean verify = userService.verifyUser(uuidRequest.getUuid());
        if (verify) {
            return ResponseEntity.ok("인증 되었습니다!");
        }
        return ResponseEntity.ok("이메일 및 인증코드를 확인해주세요!");
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> loginUser(@RequestBody LoginUserRequest loginUserRequest) {
//        User user = userService.loginUser(loginUserRequest.getLoginId(), loginUserRequest.getPassword());
//
//        // JWT 토큰 생성
//        String token = jwtUtil.createToken(user.getLoginId(), user.getUserName());
//
//        // 로그인 성공 시 JWT 토큰을 반환
//        return ResponseEntity.ok(token);
//    }
//
//    @GetMapping("/success")
//    public ResponseEntity<?> oauth2LoginSuccess(Authentication authentication) {
//        // 소셜 로그인 성공 후 사용자 정보로 JWT 발급
//        String token = jwtUtil.createJwt(authentication.getName(), authentication.getAuthorities().toString());
//        return ResponseEntity.ok(new JwtResponse(token));
//    }
//
//    @GetMapping("/failure")
//    public ResponseEntity<?> oauth2LoginFailure() {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("소셜 로그인 실패");
//    }
}