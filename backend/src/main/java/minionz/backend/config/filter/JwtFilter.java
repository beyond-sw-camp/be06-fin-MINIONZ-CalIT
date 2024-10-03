package minionz.backend.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minionz.backend.utils.JwtUtil;
import minionz.backend.user.model.CustomSecurityUserDetails;
import minionz.backend.user.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    log.info("JwtFilter가 호출되었습니다: " + request.getRequestURI()); // 요청 URI 로그
    try {
      String authorization = null;
      if (request.getCookies() != null) {
        for (Cookie cookie : request.getCookies()) {
          if (cookie.getName().equals("ATOKEN")) {
            authorization = cookie.getValue();
          }
        }
      }

      if (request.getRequestURI().equals("/health")) {
        log.info("Health check 요청이므로 인증 쿠키를 확인하지 않습니다.");
        filterChain.doFilter(request, response);
        return;
      }

      if (authorization == null) {
        log.info("인증 쿠키 없음");
        filterChain.doFilter(request, response);
        return;
      }
      // String token = authorization.split(" ")[1];
      String token = authorization;
      if (jwtUtil.isExpired(token)) {
        filterChain.doFilter(request, response);
        return;
      }
      User user = new User();
      user.setUserId(jwtUtil.getUserId(token));
      user.setLoginId(jwtUtil.getLoginId(token));
      user.setRole(jwtUtil.getRole(token));
      user.setUserName(jwtUtil.getUserName(token));
      CustomSecurityUserDetails customUserDetails = new CustomSecurityUserDetails(user);
      Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null,
          customUserDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authToken);
      filterChain.doFilter(request, response);
    } catch (ServletException e) {
      throw new ServletException(e);
    }
  }
}