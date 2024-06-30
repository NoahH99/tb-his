package com.noahhendrickson.bot.exception;

import com.noahhendrickson.bot.dto.ErrorResponse;

public class RegisterUserException extends RuntimeException {

    private final ErrorResponse errorResponse;

    public RegisterUserException(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}
