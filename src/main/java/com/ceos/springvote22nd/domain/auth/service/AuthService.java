package com.ceos.springvote22nd.domain.auth.service;

import com.ceos.springvote22nd.domain.auth.dto.request.LoginRequestDTO;
import com.ceos.springvote22nd.domain.auth.dto.response.LoginResponseDTO;
import com.ceos.springvote22nd.domain.auth.exception.AuthErrorCode;
import com.ceos.springvote22nd.domain.user.repository.UserRepository;
import com.ceos.springvote22nd.entity.User;
import com.ceos.springvote22nd.global.config.exception.GlobalException;
import com.ceos.springvote22nd.global.config.jwt.CookieUtil;
import com.ceos.springvote22nd.global.config.jwt.JwtProvider;
import com.ceos.springvote22nd.global.config.jwt.JwtValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;
    private final JwtValidator jwtValidator;
    private final CookieUtil cookieUtil;

    @Transactional
    public LoginResponseDTO login(LoginRequestDTO request, HttpServletResponse response) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new GlobalException(AuthErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new GlobalException(AuthErrorCode.INVALID_CREDENTIALS);
        }

        String accessToken = jwtProvider.createAccessToken(user.getId(), user.getEmail());
        String refreshToken = jwtProvider.createRefreshToken(user.getId());

        cookieUtil.addAccessTokenCookie(response, accessToken);
        cookieUtil.addRefreshTokenCookie(response, refreshToken);

        return LoginResponseDTO.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .team(user.getTeam())
                .part(user.getPart())
                .build();
    }

    @Transactional
    public void refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = cookieUtil.getRefreshTokenFromCookie(request); // 없으면 REFRESH_TOKEN_NOT_FOUND

        if (!jwtValidator.validateToken(refreshToken)) {
            throw new GlobalException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }

        String tokenType = jwtValidator.getTokenType(refreshToken);
        if (!"refresh".equals(tokenType)) {
            throw new GlobalException(AuthErrorCode.INVALID_TOKEN_TYPE);
        }

        Long userId = jwtValidator.getUserIdFromToken(refreshToken);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GlobalException(AuthErrorCode.USER_NOT_FOUND));

        // rotation
        String newAccessToken = jwtProvider.createAccessToken(user.getId(), user.getEmail());
        String newRefreshToken = jwtProvider.createRefreshToken(user.getId());

        cookieUtil.addAccessTokenCookie(response, newAccessToken);
        cookieUtil.addRefreshTokenCookie(response, newRefreshToken);
    }

    @Transactional
    public void logout(HttpServletResponse response) {
        cookieUtil.deleteAccessTokenCookie(response);
        cookieUtil.deleteRefreshTokenCookie(response);
    }
}
