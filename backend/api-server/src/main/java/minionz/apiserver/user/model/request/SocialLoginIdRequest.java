package minionz.apiserver.user.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SocialLoginIdRequest {
    String email;
    String loginId;
}
