package minionz.backend.user;

import lombok.RequiredArgsConstructor;
import minionz.backend.user.model.User;
import minionz.backend.user.model.request.CreateUserRequest;
import minionz.backend.user.model.request.LoginUserRequest;
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

    public void signupUser(CreateUserRequest createUserRequest) {
        String verificationCode = UUID.randomUUID().toString();
        User user = User.builder()
                .loginId(createUserRequest.getLoginId())
                .password(bCryptPasswordEncoder.encode(createUserRequest.getPassword()))
                .email(createUserRequest.getEmail())
                .userName(createUserRequest.getUserName())
                .verificationCode(verificationCode)
                .build();           //유저 객체 값 설정
        userRepository.save(user);  //유저 저장

        emailVerifyService.sendVerificationEmail(user.getEmail(), verificationCode); //저장된 유저의 이메일로 인증 코드 발송
    }

    public boolean verifyUser(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);  //입력받은 인증 코드로 DB에서 유저 찾기

        if (user == null || user.isEnabled()) {
            return false; // 인증 실패
        }

        user.setEnabled(true); // 인증 상태 성공으로 변경
        user.setVerificationCode(null); // 인증 코드 초기화
        userRepository.save(user); // 인증 코드는 초기화 되고 인증 상태는 true인 상태로 해당 유저 업데이트
        return true;
    }

    //ID 중복 여부 확인 메서드
    public boolean checkLoginDuplicate(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    public User loginUser(LoginUserRequest loginUserRequest) {
        User loginuser = userRepository.findByLoginId(loginUserRequest.getLoginId());

        if(loginuser == null) {
            return null;
        }

        if(!loginUserRequest.getPassword().equals(loginuser.getPassword())) {
            return null;
        }

        return loginuser;
    }

    public User getLoginUserByLoginId(String loginId) {
        if(loginId == null) return null;

        User user = userRepository.findByLoginId(loginId);
        if(user == null) {
            return null;
        }

        return user;
    }
}
