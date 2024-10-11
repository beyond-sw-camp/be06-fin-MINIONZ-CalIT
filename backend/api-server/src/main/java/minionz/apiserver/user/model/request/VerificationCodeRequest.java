package minionz.apiserver.user.model.request;

import lombok.*;

//인증 코드를 저장하는 객체
@Getter
@Setter
public class VerificationCodeRequest {
    private String code;
}

