package com.noahhendrickson.api.common.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ErrorResponseDTOTest {

    @Test
    void testMessageGetter() {
        String message = "Not found";
        String errorCode = "404";
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(message, errorCode);

        String actualMessage = errorResponse.getMessage();

        assertEquals(message, actualMessage);
    }

    @Test
    void testErrorCodeGetter() {
        String message = "Not found";
        String errorCode = "404";

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(message, errorCode);

        String actualErrorCode = errorResponse.getErrorCode();

        assertEquals(errorCode, actualErrorCode);
    }

    @Test
    public void testNullMessage() {
        String message = null;
        String errorCode = "404";

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(message, errorCode);

        assertNull(errorResponse.getMessage());

    }

    @Test
    public void testEmptyErrorCode() {
        String message = "Not found";
        String errorCode = "";

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(message, errorCode);

        assertEquals("", errorResponse.getErrorCode());
    }
}
