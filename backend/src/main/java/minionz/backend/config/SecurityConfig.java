package minionz.backend.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import minionz.backend.config.filter.*;
import minionz.backend.user.CustomOauth2UserService;
import minionz.backend.user.CustomUserDetailService;
import minionz.backend.user.model.CustomSecurityUserDetails;
import minionz.backend.utils.JwtUtil;
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
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtUtil jwtUtil;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtAccessDeniedHandler accessDeniedHandler;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final CustomOauth2UserService customOauth2UserService;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final CustomUserDetailService customUserDetailService;

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
    SecurityFilterChain filterChain(HttpSecurity http, HttpServletResponse response) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(Customizer.withDefaults()); // CORS 설정 추가

        http
                .formLogin(auth -> auth.disable());

//     http
//     .oauth2Login((auth) -> auth.loginPage("/user/login")
//     .defaultSuccessUrl("/social/login/success")
//     .failureUrl("/user/login")
//     .permitAll());
        http.oauth2Login((config) -> {
            config.successHandler(oAuth2AuthenticationSuccessHandler);
            config.failureHandler(oAuth2AuthenticationFailureHandler);
            config.userInfoEndpoint((endpoint) -> endpoint.userService(customOauth2UserService));
        });

        http
                .logout(logout -> logout
                        .logoutUrl("/oauth-login/logout")
                        .logoutSuccessUrl("/oauth-login/logout-success"));

        http
                .authorizeHttpRequests((requestMatcher) -> requestMatcher
                        // .requestMatchers("/oauth-login/**", "/user/login").permitAll() // Allow
                        // access to these paths without authentication
                        // .anyRequest().authenticated());
                        // 워크스페이스 관련 요청
                        .requestMatchers(HttpMethod.GET, "/workspace")
                        .access((auth, context) -> hasAuthorities(auth, RoleConstants.ROLE_WORKSPACE_MEMBER, context, response))
                        .requestMatchers("/workspace/**").permitAll()
                        // 스프린트 관련 요청
                        .requestMatchers(HttpMethod.GET, "/sprint/**")
                        .access((auth, context) -> hasAuthorities(auth, RoleConstants.ROLE_WORKSPACE_MEMBER, context, response))
                        .requestMatchers(HttpMethod.POST, "/sprint/**")
                        .access((auth, context) -> hasAuthorities(auth, RoleConstants.ROLE_WORKSPACE_MEMBER, context, response))
                        .requestMatchers("/sprint/**")
                        .access((auth, context) -> hasAuthorities(auth, RoleConstants.ROLE_SPRINT_ADMIN, context, response))
                        // 태스크 관련 요청
                        .requestMatchers(HttpMethod.DELETE, "/task/**")
                        .access((auth, context) -> hasAuthorities(auth, RoleConstants.ROLE_SPRINT_ADMIN, context, response))
                        .requestMatchers("/task/**")
                        .access((auth, context) -> hasAuthorities(auth, RoleConstants.ROLE_SPRINT_MEMBER, context, response))
                        // 이슈 관련 요청
                        .requestMatchers(HttpMethod.DELETE, "/issue/**")
                        .access((auth, context) -> hasAuthorities(auth, RoleConstants.ROLE_WORKSPACE_ADMIN, context, response))
                        .requestMatchers("/issue/**")
                        .access((auth, context) -> hasAuthorities(auth, RoleConstants.ROLE_WORKSPACE_MEMBER, context, response))
                        // 회의 관련 요청
                        .requestMatchers(HttpMethod.DELETE, "/meeting/**")
                        .access((auth, context) -> hasAuthorities(auth, RoleConstants.ROLE_SPRINT_ADMIN, context, response))
                        .requestMatchers("/meeting/**")
                        .access((auth, context) -> hasAuthorities(auth, RoleConstants.ROLE_SPRINT_MEMBER, context, response))
                        // 라벨 관련 요청
                        .requestMatchers("/label/**")
                        .access((auth, context) -> hasAuthorities(auth, RoleConstants.ROLE_WORKSPACE_MEMBER, context, response))
                        // 대시보드 관련 요청
                        .requestMatchers("/dashboard/**")
                        .access((auth, context) -> hasAuthorities(auth, RoleConstants.ROLE_WORKSPACE_MEMBER, context, response))
                        // 스케줄 관련 요청
                        .requestMatchers("/schedule/**")
                        .access((auth, context) -> hasAuthorities(auth, RoleConstants.ROLE_WORKSPACE_MEMBER, context, response))
                        // .requestMatchers("/errboard/**").access((auth, context) ->
                        // hasAuthorities(auth, RoleConstants.ROLE_WORKSPACE_MEMBER, context))
                        // .requestMatchers("/errcomment/**").access((auth, context) ->
                        // hasAuthorities(auth, RoleConstants.ROLE_WORKSPACE_MEMBER, context))
                        // .requestMatchers("/qaboard/**").access((auth, context) ->
                        // hasAuthorities(auth, RoleConstants.ROLE_WORKSPACE_MEMBER, context))
                        // .requestMatchers("/qacomment/**").access((auth, context) ->
                        // hasAuthorities(auth, RoleConstants.ROLE_WORKSPACE_MEMBER, context))
                        // .requestMatchers(HttpMethod.POST,"/note/**").access((auth, context) ->
                        // hasAuthorities(auth, RoleConstants.ROLE_MEETING_MEMBER, context))
                        .anyRequest().permitAll());

        http
                .exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedHandler(accessDeniedHandler));

        http.addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class);
        LoginFilter loginFilter = new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil);
        loginFilter.setFilterProcessesUrl("/user/login");
        http.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    private AuthorizationDecision hasAuthorities(Supplier<Authentication> authentication, String requiredRole,
                                                 RequestAuthorizationContext object, HttpServletResponse response) {
        if (object.getRequest().getRequestURI().split("/")[2].equals("my")) {
            return new AuthorizationDecision(true);
        }

        String number = object.getRequest().getRequestURI().split("/")[2];
        String role = number.equals("all") ? requiredRole + "_" + object.getRequest().getRequestURI().split("/")[3]
                : requiredRole + "_" + number;

        List<String> roles = parseRoles(authentication);

        if (!roles.contains(role)) {
            CustomSecurityUserDetails customUserDetails = customUserDetailService.loadUserByUsername(((CustomSecurityUserDetails) authentication.get().getPrincipal()).getLoginId());

            Collection<? extends GrantedAuthority> authorities = customUserDetails.getAuthorities();

            roles = authorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            ObjectMapper objectMapper = new ObjectMapper();
            String rolesJson = "[]";

            try {
                rolesJson = objectMapper.writeValueAsString(roles);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            if (roles.contains(role)) {
                String token = jwtUtil.createToken(  customUserDetails.getLoginId(), customUserDetails.getUserId(), rolesJson,customUserDetails.getUserName());
                Cookie aToken = new Cookie("ATOKEN", token);
                aToken.setHttpOnly(true);
                aToken.setSecure(true);
                aToken.setPath("/");
                aToken.setMaxAge(60 * 60 * 100);
                response.addCookie(aToken);

                response.addHeader("Authorization", "Bearer " + token);
            }
        }

        return new AuthorizationDecision(roles.contains(role));
    }

    private List<String> parseRoles(Supplier<Authentication> authentication) {
        Optional<? extends GrantedAuthority> firstAuthority = authentication.get().getAuthorities().stream().findFirst();
        String jsonString = firstAuthority.map(GrantedAuthority::getAuthority).orElse("");

        try {
            return new ObjectMapper().readValue(jsonString, new TypeReference<List<String>>() {
            });
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