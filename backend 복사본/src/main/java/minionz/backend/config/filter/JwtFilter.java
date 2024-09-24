package minionz.backend.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minionz.backend.Utils.JwtUtil;
import minionz.backend.user.UserService;
import minionz.backend.user.model.CustomSecurityUserDetails;
import minionz.backend.user.model.User;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserService userService; // UserService나 UserRepository를 사용하여 유저 정보를 가져옴

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws  ServletException, IOException, InternalAuthenticationServiceException {
        try{
            String authorization = null;
            if(request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if (cookie.getName().equals("ATOKEN")) {
                        authorization = cookie.getValue();
                    }
                }
            }
            if(authorization == null){
                log.info("인증 쿠키 없음");
                filterChain.doFilter(request, response);
                return;
            }
//            String token = authorization.split(" ")[1];
            String token = authorization;
            if(jwtUtil.isExpired(token)){
                filterChain.doFilter(request, response);
                return;
            }
            String id = jwtUtil.getLoginId(token);

            User user = userService.getLoginUserByLoginId(id);
            log.info(id + " ");
            CustomSecurityUserDetails customSecurityUserDetails = new CustomSecurityUserDetails(user);
            Authentication authToken = new UsernamePasswordAuthenticationToken(customSecurityUserDetails, null, customSecurityUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
            filterChain.doFilter(request, response);
        }catch (ServletException e){
            log.error("인증 중 오류 발생", e);
            throw new ServletException(e);
        }
    }
}
