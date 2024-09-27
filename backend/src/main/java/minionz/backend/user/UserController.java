package minionz.backend.user;


import minionz.backend.common.responses.BaseResponse;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.utils.JwtUtil;
import minionz.backend.user.model.request.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public BaseResponse<BaseResponseStatus> signupUser(@RequestBody CreateUserRequest createUserRequest) {
        System.out.println(createUserRequest);
        userService.signupUser(createUserRequest);
        return new BaseResponse<>(BaseResponseStatus.USER_CREATE_SUCCESS);
    }

    @GetMapping("/check-id")
    public BaseResponse<BaseResponseStatus> checkUserId(@RequestParam String loginId) {
        boolean duplicate = userService.checkLoginDuplicate(loginId);
        if (duplicate) {
            return new BaseResponse<>(BaseResponseStatus.USER_ID_DUPLICATE);
        } else {
            return new BaseResponse<>(BaseResponseStatus.USER_ID_NOT_DUPLICATE);
        }
    }

    @PostMapping("/send-verificationcode")
    public BaseResponse<BaseResponseStatus> sendVerificationCode(@RequestBody EmailVerifyRequest emailVerifyRequest) {
        String email = emailVerifyRequest.getEmail();
        boolean duplicate = userService.sendVerificationCode(email);
        if (duplicate) {
            return new BaseResponse<>(BaseResponseStatus.USER_EMAIL_DUPLICATE);
        } else {
            return new BaseResponse<>(BaseResponseStatus.USER_EMAIL_NOT_DUPLICATE);
        }
    }

    @PostMapping("/confirm-verificationcode")
    public BaseResponse<BaseResponseStatus> confirmVerificationCode(@RequestBody UuidRequest uuidRequest) {
        boolean verify = userService.verifyUser(uuidRequest.getUuid());
        if (verify) {
            return new BaseResponse<>(BaseResponseStatus.USER_UUID_VALID);
        }
        return new BaseResponse<>(BaseResponseStatus.USER_UUID_INVALID);
    }
}