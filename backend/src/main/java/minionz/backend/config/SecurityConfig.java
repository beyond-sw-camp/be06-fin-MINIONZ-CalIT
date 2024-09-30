package minionz.backend.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import minionz.backend.config.filter.JwtAccessDeniedHandler;
import minionz.backend.config.filter.JwtFilter;
import minionz.backend.utils.JwtUtil;
import minionz.backend.config.filter.LoginFilter;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtUtil jwtUtil;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtAccessDeniedHandler accessDeniedHandler;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(String.valueOf(PathRequest.toStaticResources().atCommonLocations()));
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(Customizer.withDefaults()); // CORS 설정 추가

        http
                .formLogin(auth -> auth
                        .loginPage("/user/login")
                        .usernameParameter("loginId")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/my/dashboard")
                        .failureUrl("/user/login")
                        .permitAll());

        http
                .logout(logout -> logout
                        .logoutUrl("/oauth-login/logout")
                        .logoutSuccessUrl("/oauth-login/logout-success"));

        http
                .authorizeHttpRequests((requestMatcher) -> requestMatcher
//                        .requestMatchers("/oauth-login/**", "/api/user/login").permitAll()  // Allow access to these paths without authentication
//                        .anyRequest().authenticated());
                        // 워크스페이스 관련 요청
                        .requestMatchers(HttpMethod.GET, "/api/workspace").access((auth, context) -> hasAuthorities(auth, RoleConstants.ROLE_WORKSPACE_MEMBER, context))
                        .requestMatchers(HttpMethod.POST, "/api/workspace/**").permitAll()
                        .requestMatchers("/api/workspace/**").access((auth, context) -> hasAuthorities(auth, RoleConstants.ROLE_SPRINT_ADMIN, context))
                        // 스프린트 관련 요청
                        .requestMatchers(HttpMethod.GET, "/api/sprint/**").access((auth, context) -> hasAuthorities(auth, RoleConstants.ROLE_WORKSPACE_MEMBER, context))
                        .requestMatchers(HttpMethod.POST, "/api/sprint/**").access((auth, context) -> hasAuthorities(auth, RoleConstants.ROLE_WORKSPACE_MEMBER, context))
                        .requestMatchers("/api/sprint/**").access((auth, context) -> hasAuthorities(auth, RoleConstants.ROLE_SPRINT_ADMIN, context))
                        // 태스크 관련 요청
                        .requestMatchers(HttpMethod.DELETE, "/api/task/**").access((auth, context) -> hasAuthorities(auth, RoleConstants.ROLE_SPRINT_ADMIN, context))
                        .requestMatchers("/api/task/**").access((auth, context) -> hasAuthorities(auth, RoleConstants.ROLE_SPRINT_MEMBER, context))
                        // 이슈 관련 요청
                        .requestMatchers(HttpMethod.DELETE, "/api/issue/**").access((auth, context) -> hasAuthorities(auth, RoleConstants.ROLE_WORKSPACE_ADMIN, context))
                        .requestMatchers("/api/issue/**").access((auth, context) -> hasAuthorities(auth, RoleConstants.ROLE_WORKSPACE_MEMBER, context))
                        // 회의 관련 요청
                        .requestMatchers(HttpMethod.DELETE, "/api/meeting/**").access((auth, context) -> hasAuthorities(auth, RoleConstants.ROLE_SPRINT_ADMIN, context))
                        .requestMatchers("/api/meeting/**").access((auth, context) -> hasAuthorities(auth, RoleConstants.ROLE_SPRINT_MEMBER, context))
                        // 라벨 관련 요청
                        .requestMatchers("/api/label/**").access((auth, context) -> hasAuthorities(auth, RoleConstants.ROLE_WORKSPACE_MEMBER, context))
                        // 대시보드 관련 요청
                        .requestMatchers("/api/dashboard/**").access((auth, context) -> hasAuthorities(auth, RoleConstants.ROLE_WORKSPACE_MEMBER, context))
                        // 스케줄 관련 요청
                        .requestMatchers("/api/schedule/**").access((auth, context) -> hasAuthorities(auth, RoleConstants.ROLE_WORKSPACE_MEMBER, context))
//                        .requestMatchers("/api/errboard/**").access((auth, context) -> hasAuthorities(auth, RoleConstants.ROLE_WORKSPACE_MEMBER, context))
//                        .requestMatchers("/api/errcomment/**").access((auth, context) -> hasAuthorities(auth, RoleConstants.ROLE_WORKSPACE_MEMBER, context))
//                        .requestMatchers("/api/qaboard/**").access((auth, context) -> hasAuthorities(auth, RoleConstants.ROLE_WORKSPACE_MEMBER, context))
//                        .requestMatchers("/api/qacomment/**").access((auth, context) -> hasAuthorities(auth, RoleConstants.ROLE_WORKSPACE_MEMBER, context))
//                        .requestMatchers(HttpMethod.POST,"/api/note/**").access((auth, context) -> hasAuthorities(auth, RoleConstants.ROLE_MEETING_MEMBER, context))
                        .anyRequest().permitAll());

        http
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.accessDeniedHandler(accessDeniedHandler)
                );

        http.addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class);
        LoginFilter loginFilter = new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil);
        loginFilter.setFilterProcessesUrl("/api/user/login");
        http.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    private AuthorizationDecision hasAuthorities(Supplier<Authentication> authentication, String requiredRole, RequestAuthorizationContext object) {
        if (object.getRequest().getRequestURI().split("/")[3].equals("my")) {
            return new AuthorizationDecision(true);
        }

        String number = object.getRequest().getRequestURI().split("/")[3];
        String role = number.equals("all") ? requiredRole + "_" +  object.getRequest().getRequestURI().split("/")[4] : requiredRole + "_" + number;

        List<String> roles = parseRoles(authentication);
        return new AuthorizationDecision(roles.contains(role));
    }

    private List<String> parseRoles(Supplier<Authentication> authentication) {
        Optional<? extends GrantedAuthority> firstAuthority = authentication.get().getAuthorities().stream().findFirst();
        String jsonString = firstAuthority.map(GrantedAuthority::getAuthority).orElse("");

        try {
            return new ObjectMapper().readValue(jsonString, new TypeReference<List<String>>() {});
        } catch (IOException e) {
            throw new AccessDeniedException("Failed to parse roles", e);
        }
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

    public static class RoleConstants {
        public static final String ROLE_WORKSPACE_ADMIN = "ROLE_WORKSPACE_ADMIN";
        public static final String ROLE_WORKSPACE_MEMBER = "ROLE_WORKSPACE_MEMBER";
        public static final String ROLE_SPRINT_ADMIN = "ROLE_SPRINT_ADMIN";
        public static final String ROLE_SPRINT_MEMBER = "ROLE_SPRINT_MEMBER";
        public static final String ROLE_MEETING_MEMBER = "ROLE_MEETING_MEMBER";
    }
}