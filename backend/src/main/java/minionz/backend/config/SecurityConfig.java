package minionz.backend.config;

import lombok.RequiredArgsConstructor;
import minionz.backend.config.filter.JwtFilter;
import minionz.backend.utils.JwtUtil;
import minionz.backend.config.filter.LoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtUtil jwtUtil;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//        http
//                .formLogin((auth) -> auth.loginPage("/oauth-login/login")
//                        .loginProcessingUrl("/oauth-login/loginProc")
//                        .usernameParameter("loginId")
//                        .passwordParameter("password")
//                        .defaultSuccessUrl("/oauth-login")
//                        .failureUrl("/oauth-login")
//                        .permitAll());

        http
                .oauth2Login(oauth -> oauth
                        .loginPage("/oauth-login/login")  // 로그인 페이지 설정
                        .defaultSuccessUrl("/oauth-login/success")  // 로그인 성공 시 리디렉션 경로
                        .failureUrl("/oauth-login/failure")  // 로그인 실패 시 리디렉션 경로
                        .successHandler(new SimpleUrlAuthenticationSuccessHandler("/oauth-login/success")) // 성공 핸들러 추가
                        .permitAll());

        http
                .logout(logout -> logout
                        .logoutUrl("/oauth-login/logout")
                        .logoutSuccessUrl("/oauth-login/logout-success"));

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/oauth-login/**", "/user/login").permitAll()  // Allow access to these paths without authentication
                        .anyRequest().authenticated());

        http.addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class);
        LoginFilter loginFilter = new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil);
        loginFilter.setFilterProcessesUrl("/user/login");
        http.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        DefaultHttpFirewall firewall = new DefaultHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;
    }
}