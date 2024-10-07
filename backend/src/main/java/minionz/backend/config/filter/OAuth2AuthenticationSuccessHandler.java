package minionz.backend.config.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minionz.backend.utils.JwtUtil;
import minionz.backend.user.model.CustomOauth2UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOauth2UserDetails oAuth2Member = (CustomOauth2UserDetails) authentication.getPrincipal();
        Long id = oAuth2Member.getUser().getUserId();
        String userName = oAuth2Member.getUsername();
        String role = oAuth2Member.getUser().getRole();
        String email = oAuth2Member.getUser().getEmail();
        String token = jwtUtil.createToken(email , id, role, userName);
        log.info(id + " " + role + " " + userName);
        Cookie aToken = new Cookie("ATOKEN", token);
        aToken.setHttpOnly(true);
        aToken.setSecure(true);
        aToken.setPath("/");
        aToken.setMaxAge(60 * 60 * 100000);
        response.addCookie(aToken);

        log.info("로그인을 위한 메소드 실행 확인용");
        getRedirectStrategy().sendRedirect(request, response, "http://localhost:3000/social/login/success?Authorization=Bearer "+token);
    }
}


