package com.ceos.springvote22nd.domain.vote.exception;

import com.ceos.springvote22nd.global.config.exception.ResultCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum VoteErrorCode implements ResultCode {

    // Vote 관련 에러
    INVALID_PART(HttpStatus.NOT_FOUND, 2101, "파트가 일치해야합니다."),
    ALREADY_VOTED(HttpStatus.CONFLICT, 2102, "한 사람은 한번만 투표할 수 있습니다.");


    private final HttpStatus status;
    private final int code;
    private final String message;
}