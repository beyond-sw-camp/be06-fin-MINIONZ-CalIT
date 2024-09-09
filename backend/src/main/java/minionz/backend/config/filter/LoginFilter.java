package minionz.backend.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minionz.backend.utils.JwtUtil;
import minionz.backend.user.model.CustomSecurityUserDetails;
import minionz.backend.user.model.request.LoginUserRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;


import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Slf4j
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        LoginUserRequest loginUserRequest;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ServletInputStream inputStream = request.getInputStream();
            String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            loginUserRequest = objectMapper.readValue(messageBody, LoginUserRequest.class);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginUserRequest.getLoginId(), loginUserRequest.getPassword(), null);
            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {

    }

    //로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {

    }
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        CustomSecurityUserDetails user = (CustomSecurityUserDetails) authResult.getPrincipal();
//        String loginId = user.getLoginId();
//        String name = user.getUserName();
//        String aTokenString = jwtUtil.createToken(loginId, name);
//        Cookie aToken = new Cookie("ATOKEN", aTokenString);
//        aToken.setHttpOnly(true);
//        aToken.setSecure(true);
//        aToken.setPath("/");
//        aToken.setMaxAge(60 * 60 * 100000);ㅁ
//        response.addCookie(aToken);
//        String combinedValue = name;
//        log.info(combinedValue);
//        Cookie uToken = new Cookie("UTOKEN", combinedValue);
//        uToken.setHttpOnly(false);
//        uToken.setSecure(false);
//        uToken.setPath("/");
//        uToken.setMaxAge(60 * 60 * 100000); // 여기도 1시간으로 설정
//        response.addCookie(uToken);
//    }
}
