package com.example.schedule.exception;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;

public enum ExceptionErrorCode {
    CONTENT_NULL(HttpStatus.BAD_REQUEST, "작성내용이 없습니다."),
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "조건에 맞는 일정이 없습니다."),
    PASSWORD_ERROR(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당ID 유저가 존재하지 않습니다."),
    CHECK_VALUE_MAIL(HttpStatus.BAD_REQUEST, "잘못된 메일주소입니다."),
    REQUEST_DTO_ERROR_CONTENT_NULL(HttpStatus.BAD_REQUEST, "내용은 필수값입니다."),
    REQUEST_DTO_ERROR_SIZE_200(HttpStatus.BAD_REQUEST, "내용은 200자 이내로 작성해야합니다."),
    REQUEST_DTO_ERROR_PWD_NULL(HttpStatus.BAD_REQUEST, "비밀번호는 필수값 입니다.");
//    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류");

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
