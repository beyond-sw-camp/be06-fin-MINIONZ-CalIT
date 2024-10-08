package minionz.backend.user;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import minionz.backend.user.model.CustomSecurityUserDetails;
import minionz.backend.user.model.EmailVerify;
import minionz.backend.user.model.User;
import minionz.backend.user.model.request.CreateUserRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletResponse;
import minionz.backend.utils.JwtUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailVerifyService emailVerifyService;
    private final EmailVerifyRepository emailVerifyRepository;
    private final JwtUtil jwtUtil;

    public boolean signupUser(CreateUserRequest createUserRequest) {
        User user = userRepository.findByLoginId(createUserRequest.getLoginId());
        if (user == null) {
            user = User.builder()
                    .loginId(createUserRequest.getLoginId())
                    .password(bCryptPasswordEncoder.encode(createUserRequest.getPassword()))
                    .email(createUserRequest.getEmail())
                    .role("ROLE_ADMIN")
                    .isEnabled(true)
                    .userName(createUserRequest.getUserName())
                    .persona((int) Math.random() * 12 + 1)
                    .build();           //유저 객체 값 설정
            userRepository.save(user);  //유저 저장
            return true;
        } else {
            return false;
        }
    }

    public boolean sendVerificationCode(String email) {
        EmailVerify duplicate = emailVerifyRepository.findByEmail(email);
        if (duplicate == null) {
            String verificationCode = UUID.randomUUID().toString();
            emailVerifyService.sendVerificationEmail(email, verificationCode); //저장된 유저의 이메일로 인증 코드 발송
            EmailVerify emailVerify = EmailVerify.builder()
                    .email(email)
                    .uuid(verificationCode)
                    .build();
            emailVerifyRepository.save(emailVerify);
            return false;
        }
        return true;
    }

    public boolean verifyUser(String uuid) {
        EmailVerify emailVerify = emailVerifyRepository.findByUuid(uuid);//입력받은 인증 코드로 DB에서 이메일 찾기 찾기
        if (emailVerify != null) {
            emailVerify.setUuid(null);
            emailVerifyRepository.save(emailVerify);
            return true;
        }
        return false;
    }

    //ID 중복 여부 확인 메서드
    public boolean checkLoginDuplicate(String loginId) {
        boolean duplicate = userRepository.existsByLoginId(loginId);
        return duplicate;
    }

    public User loginUser(String loginId, String password) {
        User loginuser = userRepository.findByLoginId(loginId);

        if (loginuser == null) {
            return null;
        }

        if (bCryptPasswordEncoder.matches(loginuser.getPassword(), password)) {
            return null;
        }

        return loginuser;
    }

    public User getLoginUserByLoginId(String loginId) {
        if (loginId == null) return null;

        User user = userRepository.findByLoginId(loginId);
        if (user == null) {
            return null;
        }

        return user;
    }

    @Transactional
    public void sendNewToken(CustomSecurityUserDetails userDetails, HttpServletResponse response) {
        User user = userRepository.findByLoginId(userDetails.getLoginId());

        String token = jwtUtil.createToken(user);
        Cookie aToken = new Cookie("ATOKEN", token);
        aToken.setHttpOnly(true);
        aToken.setSecure(true);
        aToken.setPath("/");
        aToken.setMaxAge(60 * 60 * 100);
        response.addCookie(aToken);

        response.addHeader("Authorization", "Bearer " + token);
    }
}