//package minionz.backend.config.filter;
//
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.JwtException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Slf4j
//public class ExceptionFilter extends OncePerRequestFilter {
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        try{
//            filterChain.doFilter(request, response);
//        } catch (ExpiredJwtException e){
//            log.error("Bearer 토큰이 만료되었습니다.");
//        } catch (JwtException | IllegalArgumentException e){
//            log.error("Bearer 토큰이 유효하지 않습니다.");
//        }
//    }
//}
