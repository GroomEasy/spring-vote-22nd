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

        cookieUtil.addAccessTokenCookie(response, accessToken);

        return LoginResponseDTO.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .team(user.getTeam())
                .part(user.getPart())
                .build();
    }

    @Transactional
    public void logout(HttpServletResponse response) {
        cookieUtil.deleteAccessTokenCookie(response);
    }
}
