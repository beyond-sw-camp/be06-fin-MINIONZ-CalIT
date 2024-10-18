package minionz.apiserver.config.filter;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

public class CustomLogoutHandler implements org.springframework.security.web.authentication.logout.LogoutHandler {
    /**
     * Causes a logout to be completed. The method must complete successfully.
     *
     * @param request        the HTTP request
     * @param response       the HTTP response
     * @param authentication the current principal details
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Cookie cookie = new Cookie("ATOKEN", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // Invalidate cookie
        response.addCookie(cookie);
    }
}
