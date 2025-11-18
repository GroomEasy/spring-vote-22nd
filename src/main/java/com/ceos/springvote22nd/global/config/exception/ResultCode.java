package com.ceos.springvote22nd.global.config.exception;

import org.springframework.http.HttpStatus;

public interface ResultCode {
    HttpStatus getStatus();
    int getCode();
    String getMessage();
}
