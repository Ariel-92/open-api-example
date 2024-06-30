package com.rockercats.open_api.exception;

public class NoContentException extends RuntimeException {
    public NoContentException() {}

    public NoContentException(String message) {
        super(message);
    }

    public NoContentException(String message, Throwable cause) {
        super(message, cause);
    }
}
