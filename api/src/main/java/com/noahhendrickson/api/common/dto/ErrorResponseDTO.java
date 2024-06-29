package com.noahhendrickson.api.common.dto;

public class ErrorResponseDTO {

    private final String message;
    private final String errorCode;

    public ErrorResponseDTO(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
