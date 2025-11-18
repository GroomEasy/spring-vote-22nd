package com.ceos.springvote22nd.domain.user.service;

import com.ceos.springvote22nd.domain.auth.exception.AuthErrorCode;
import com.ceos.springvote22nd.domain.user.dto.request.SignUpRequestDTO;
import com.ceos.springvote22nd.domain.user.dto.response.SignUpResponseDTO;
import com.ceos.springvote22nd.domain.user.exception.UserErrorCode;
import com.ceos.springvote22nd.domain.user.repository.UserRepository;
import com.ceos.springvote22nd.entity.User;
import com.ceos.springvote22nd.global.config.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignUpResponseDTO signUp(SignUpRequestDTO request) {
        // 비밀번호 일치 검증
        if (!request.isPasswordMatch()) {
            throw new GlobalException(UserErrorCode.INVALID_PASSWORD);
        }

        // 이메일 중복 검사
        validateDuplicateEmail(request.getEmail());

        // 아이디 중복 검사
        validateDuplicateUsername(request.getUsername());


        // User 엔티티 생성
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .part(request.getPart())
                .team(request.getTeam())
                .build();

        // 저장
        User savedUser = userRepository.save(user);

        // 응답 생성
        return SignUpResponseDTO.builder()
                .userId(savedUser.getId())
                .email(savedUser.getEmail())
                .username(savedUser.getUsername())
                .part(savedUser.getPart())
                .team(savedUser.getTeam())
                .createdAt(savedUser.getCreatedAt())
                .build();
    }

    // 관련 메서드

    private void validateDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new GlobalException(UserErrorCode.DUPLICATE_EMAIL);
        }
    }

    private void validateDuplicateUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new GlobalException(UserErrorCode.DUPLICATE_USERNAME);
        }
    }


}