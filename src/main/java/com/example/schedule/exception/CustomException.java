package com.example.schedule.exception;

import org.springframework.http.HttpStatus;


public class CustomException extends RuntimeException {
    /*******************************
     * 커스텀 예외클래스
     * HTTP 상태코드, 에러멤시지
     *******************************/

    private final HttpStatus status;

    public CustomException(ExceptionErrorCode exceptionErrorCode) {

        super(exceptionErrorCode.getMessage());
        this.status = exceptionErrorCode.getStatus();
    }

    public HttpStatus getStatus() {
        return status;
    }
}
