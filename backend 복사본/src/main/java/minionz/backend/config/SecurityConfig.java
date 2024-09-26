package minionz.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 시큐리티 필터 메서드
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((auth) -> auth
                        // 인가 동작 순서 : 위에서 부터 아래로 순서대로 ! 따라서 순서 유의 (anyRequest 특히)

                        .requestMatchers("/", "/user/login", "/user/signup").permitAll()
                        // ** : 와일드카드
                        .anyRequest().authenticated()
                );

        // 로그인 설정
        http
                .formLogin((auth) -> auth.loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        .permitAll()
                );

        // 로그아웃 URL 설정
        http
                .logout((auth) -> auth
                        .logoutUrl("/logout")
                );

        http
                .formLogin((auth) -> auth.loginPage("/security-login/login")
                        .loginProcessingUrl("/security-login/loginProc")
                        .failureUrl("/security-login/login")
                        .defaultSuccessUrl("/security-login")
                        .usernameParameter("loginId")
                        .passwordParameter("password")

                        .permitAll()
                );
        http
                .csrf((auth) -> auth.disable());

        return http.build();
    }

    // BCrypt password encoder를 리턴하는 메서드
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }
}