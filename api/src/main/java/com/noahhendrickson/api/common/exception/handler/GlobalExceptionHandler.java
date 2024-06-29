package com.noahhendrickson.api.common.exception.handler;

import com.noahhendrickson.api.common.dto.ErrorResponseDTO;
import com.noahhendrickson.api.common.event.LogEventPublisher;
import com.noahhendrickson.api.common.exception.*;
import com.noahhendrickson.api.user.entity.AccountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final LogEventPublisher logEventPublisher;

    @Autowired
    public GlobalExceptionHandler(LogEventPublisher logEventPublisher) {
        this.logEventPublisher = logEventPublisher;
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleCourseNotFoundException(CourseNotFoundException exception) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(exception.getMessage(), "COURSE_NOT_FOUND");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HoleInfoNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleHoleInfoNotFoundException(HoleInfoNotFoundException exception) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(exception.getMessage(), "HOLE_INFO_NOT_FOUND");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HoleNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleHoleNotFoundException(HoleNotFoundException exception) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(exception.getMessage(), "HOLE_NOT_FOUND");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalFieldException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalFieldException(IllegalFieldException exception) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(exception.getMessage(), "ILLEGAL_FIELD");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoundNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleRoundNotFoundException(RoundNotFoundException exception) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(exception.getMessage(), "ROUND_NOT_FOUND");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ScoreNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleScoreNotFoundException(ScoreNotFoundException exception) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(exception.getMessage(), "SCORE_NOT_FOUND");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TeeNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleTeeNotFoundException(TeeNotFoundException exception) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(exception.getMessage(), "TEE_NOT_FOUND");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserNotFoundException(UserNotFoundException exception) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(exception.getMessage(), "USER_NOT_FOUND");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        String message = exception.getMessage();

        if (!message.contains("com.noahhendrickson.api.user.entity.AccountStatus"))
            throw new RuntimeException(exception);

        ErrorResponseDTO errorResponse = new ErrorResponseDTO("'accountStatus' must be in " + Arrays.toString(AccountStatus.values()), "ACCOUNT_STATUS_NOT_FOUND");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(exception.getMessage(), "ARGUMENT_TYPE_MISMATCH");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(MethodArgumentNotValidException exception) {
        ObjectError error = exception.getBindingResult().getAllErrors().getFirst();
        String fieldName = ((FieldError) error).getField();
        String errorMessage = error.getDefaultMessage();
        String errorCode = fieldName.toUpperCase() + "_REQUIRED";

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(errorMessage, errorCode));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGlobalException(Exception exception) {
        UUID id = UUID.randomUUID();
        String message = "Internal server error with ID: " + id;
        logEventPublisher.error(message, exception);

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(message, "INTERNAL_SERVER_ERROR");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
