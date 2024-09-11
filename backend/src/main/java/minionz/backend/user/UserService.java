package minionz.backend.user;

import lombok.RequiredArgsConstructor;
import minionz.backend.user.model.EmailVerify;
import minionz.backend.user.model.User;
import minionz.backend.user.model.request.CreateUserRequest;
import minionz.backend.user.model.request.EmailVerifyRequest;
import minionz.backend.user.model.request.LoginUserRequest;
import minionz.backend.user.model.request.UuidRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailVerifyService emailVerifyService;
    private final EmailVerifyRepository emailVerifyRepository;

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
            return true;
        }
        return false;
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
        boolean success = userRepository.existsByLoginId(loginId);
        return success;
    }

    public User loginUser(String loginId, String password) {
        User loginuser = userRepository.findByLoginId(loginId);

        if (loginuser == null) {
            return null;
        }

        if (bCryptPasswordEncoder.matches(loginuser.getPassword(),password)) {
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
}