package com.example.schedule.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionErrorCode {
    CONTENT_NULL(HttpStatus.BAD_REQUEST, "작성내용이 없습니다."),
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "조건에 맞는 일정이 없습니다."),
    PASSWORD_ERROR(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");

    private final HttpStatus status;
    private final String message;

    ExceptionErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
