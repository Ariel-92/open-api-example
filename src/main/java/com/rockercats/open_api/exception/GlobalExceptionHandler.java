package com.rockercats.open_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public final class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponseError> handleException(CustomException exception) {
        ApiResponseError response = ApiResponseError.of(exception);
        HttpStatus httpStatus = exception.getErrorCode().defaultHttpStatus();

        return handleExceptionInternal(response, httpStatus);
    }

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ApiResponseError> handleException(MemberException exception) {
        ApiResponseError response = ApiResponseError.of(exception);
        HttpStatus httpStatus = exception.getErrorCode().defaultHttpStatus();
        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<?> handleException(NoContentException exception) {
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<ApiResponseError> handleExceptionInternal(ApiResponseError response, HttpStatus httpStatus) {
        return new ResponseEntity<>(response, httpStatus);
    }
}
