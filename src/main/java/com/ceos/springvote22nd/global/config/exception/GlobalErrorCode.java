package com.ceos.springvote22nd.global.config.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GlobalErrorCode implements ResultCode{
    //global
    SUCCESS(HttpStatus.OK, 0, "정상 처리 되었습니다.");

    private final HttpStatus status;
    private final int code;
    private final String message;
}
