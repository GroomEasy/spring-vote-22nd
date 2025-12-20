package com.ceos.springvote22nd.global.config.jwt;

import com.ceos.springvote22nd.domain.auth.exception.AuthErrorCode;
import com.ceos.springvote22nd.global.config.exception.GlobalException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CookieUtil {

    private final JwtProvider jwtProvider;

    private static final String ACCESS_TOKEN_NAME = "accessToken";
    private static final String REFRESH_TOKEN_NAME = "refreshToken";

    public void addAccessTokenCookie(HttpServletResponse response, String token) {
        Cookie cookie = createCookie(
                ACCESS_TOKEN_NAME,
                token,
                (int) (jwtProvider.getAccessTokenValidity() / 1000),
                "/"
        );
        response.addCookie(cookie);
    }

    public void deleteAccessTokenCookie(HttpServletResponse response) {
        Cookie cookie = createCookie(ACCESS_TOKEN_NAME, null, 0, "/");
        response.addCookie(cookie);
    }



    private Cookie createCookie(String name, String value, int maxAge, String path) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
//        cookie.setSecure(true); // HTTPS 환경에서만 전송
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        // cookie.setSameSite("Strict"); // Spring Boot 3.x에서는 별도 설정 필요
        return cookie;
    }
}
