package minionz.apiserver.config.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minionz.apiserver.user.model.CustomOauth2UserDetails;
import minionz.apiserver.utils.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

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

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        ObjectMapper objectMapper = new ObjectMapper();
        String rolesJson = "[]";

        try {
            rolesJson = objectMapper.writeValueAsString(roles);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }        String email = oAuth2Member.getUser().getEmail();
        String token = jwtUtil.createToken(email , id, rolesJson, userName);
        log.info(id + " " + rolesJson + " " + userName);
        Cookie aToken = new Cookie("ATOKEN", token);
        aToken.setHttpOnly(true);
        aToken.setSecure(true);
        aToken.setPath("/");
        aToken.setMaxAge(60 * 60 * 100000);
        response.addCookie(aToken);

        log.info("로그인을 위한 메소드 실행 확인용");
        getRedirectStrategy().sendRedirect(request, response, "https://calit.kro.kr/social/login/success?Authorization=Bearer "+token);
    }
}


