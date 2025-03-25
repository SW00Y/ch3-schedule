package com.example.schedule.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ExceptionDto> handle(CustomException e) {
        ExceptionDto exceptionDto = new ExceptionDto(e.getStatus().value(),e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(exceptionDto);
    }
}
