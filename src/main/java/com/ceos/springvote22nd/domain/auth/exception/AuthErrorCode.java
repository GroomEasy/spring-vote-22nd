package com.ceos.springvote22nd.domain.auth.exception;

import com.ceos.springvote22nd.global.config.exception.ResultCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode  implements ResultCode {

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, 1001, "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, 1002, "만료된 토큰입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, 1003, "지원되지 않는 토큰입니다."),
    MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED, 1004, "잘못된 형식의 토큰입니다."),
    INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED, 1005, "잘못된 JWT 서명입니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, 1006, "Refresh Token을 찾을 수 없습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, 1007, "유효하지 않은 Refresh Token입니다."),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, 1008, "토큰이 존재하지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, 1009, "존재하지 않는 사용자입니다."),
    INVALID_CREDENTIALS(HttpStatus.CONFLICT, 1010, "이메일 또는 비밀번호가 일치하지 않습니다."),
    INVALID_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, 1011, "토큰 타입이 올바르지 않습니다.");

    private final HttpStatus status;
    private final int code;
    private final String message;
}