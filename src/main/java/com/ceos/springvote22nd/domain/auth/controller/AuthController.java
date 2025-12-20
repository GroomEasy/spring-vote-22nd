package com.ceos.springvote22nd.domain.auth.controller;

import com.ceos.springvote22nd.domain.auth.dto.request.LoginRequestDTO;
import com.ceos.springvote22nd.domain.auth.dto.response.LoginResponseDTO;
import com.ceos.springvote22nd.domain.auth.service.AuthService;
import com.ceos.springvote22nd.domain.common.dto.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "인증 API", description = "로그인/토큰재발급/로그아웃")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "로그인", description = "이메일/비밀번호로 로그인하고 access/refresh 토큰을 쿠키로 발급한다.")
    @PostMapping("/login")
    public ResponseEntity<CommonResponse<LoginResponseDTO>> login(
            @Valid @RequestBody LoginRequestDTO request,
            HttpServletResponse response
    ) {
        LoginResponseDTO result = authService.login(request, response);
        return ResponseEntity.ok(CommonResponse.success(result));
    }

    @Operation(summary = "Access 토큰 재발급", description = "refreshToken 쿠키를 검증해 accessToken(및 refreshToken)을 재발급한다.")
    @PostMapping("/refresh")
    public ResponseEntity<CommonResponse<Void>> refresh(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        authService.refresh(request, response);
        return ResponseEntity.ok(CommonResponse.success(null));
    }

    @Operation(summary = "로그아웃", description = "access/refresh 토큰 쿠키를 삭제한다.")
    @PostMapping("/logout")
    public ResponseEntity<CommonResponse<Void>> logout(HttpServletResponse response) {
        authService.logout(response);
        return ResponseEntity.ok(CommonResponse.success(null));
    }
}
