package com.rockercats.open_api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record ApiResponseError(String code, Integer status, String name, String message,
                               @JsonInclude(JsonInclude.Include.NON_EMPTY) List<ApiSimpleError> cause, Instant timestamp) {
    public static ApiResponseError of(CustomException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        String errorName = exception.getClass().getName();

        return ApiResponseError.builder()
                .code(errorCode.name())
                .status(errorCode.defaultHttpStatus().value())
                .name(errorName)
                .message(exception.getMessage())
                .cause(ApiSimpleError.listOfCauseSimpleError(exception.getCause()))
                .build();
    }

    public ApiResponseError {
        if (code == null) {
            code = "API ERROR";
        }
        if (status == null) {
            status = 500;
        }
        if (name == null) {
            name = "API ERROR";
        }
        if (message == null || message.isBlank()) {
            message = "API ERROR";
        }
        if(timestamp == null) {
            timestamp = Instant.now();
        }
    }
}
