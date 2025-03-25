package com.example.schedule.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private final HttpStatus status;

    public CustomException(ExceptionErrorCode exceptionErrorCode) {
        super(exceptionErrorCode.getMessage());
        this.status = exceptionErrorCode.getStatus();
    }

    public HttpStatus getStatus() {
        return status;
    }
}
