package com.noahhendrickson.api.common.exception;

import org.springframework.http.HttpStatus;

public class IllegalFieldException extends RuntimeException {

    private final HttpStatus httpStatus;

    public IllegalFieldException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public IllegalFieldException(String message) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }
}
