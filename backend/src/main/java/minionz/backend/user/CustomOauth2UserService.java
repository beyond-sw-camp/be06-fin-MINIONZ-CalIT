package minionz.backend.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minionz.backend.user.model.CustomOauth2UserDetails;
import minionz.backend.user.model.KakaoUserDetails;
import minionz.backend.user.model.OAuth2UserInfo;
import minionz.backend.user.model.User;
import minionz.backend.user.model.response.GoogleUserDetails;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOauth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("getAttributes : {}",oAuth2User.getAttributes());

        String provider = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo oAuth2UserInfo = null;

        // 로그인 소셜 미디어 구분 로직
        if(provider.equals("google")){
            log.info("구글 로그인");
            oAuth2UserInfo = new GoogleUserDetails(oAuth2User.getAttributes());
        } else if (provider.equals("kakao")){
            log.info("카카오 로그인");
            oAuth2UserInfo = new KakaoUserDetails(oAuth2User.getAttributes());
        } else if (provider.equals("naver")){
            log.info("네이버 로그인");
            oAuth2UserInfo = new NaverUserDetails(oAuth2User.getAttributes());
        }

        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();
        String loginId = provider + "_" + providerId;
        String name = oAuth2UserInfo.getName();
        User findUser = userRepository.findByLoginId(loginId);
        User user;

        if (findUser == null) {
            user = User.builder()
                    .loginId(loginId)
                    .email(email)
                    .userName(name)
                    .role("ROLE_ADMIN")
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(user);
        } else{
            user = findUser;
        }

        return new CustomOauth2UserDetails(user, oAuth2User.getAttributes());
    }
}