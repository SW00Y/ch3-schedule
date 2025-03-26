package com.example.schedule.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /*******************************
     * 전역 예외처리 핸들러 JSON응답으로 반환
     * @param e (CustomException) = 발생한 커스텀 예외클래스 객체
     * @return ExceptionDto(상태코드 + 메세지)
     *******************************/

    @ExceptionHandler
    public ResponseEntity<ExceptionDto> handle(CustomException e) {

        ExceptionDto exceptionDto = new ExceptionDto(e.getStatus().value(),e.getMessage());
        
        return ResponseEntity.status(e.getStatus()).body(exceptionDto);
    }

    /******
     * ScheduleRequestDto의 조건에 따른 예외처리 부분
     * @param e (MethodArgumentNotValidException) Dto 유효성 검사
     * @return ExceptionDto(상태코드 + 메세지)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> handleRequestDto(MethodArgumentNotValidException e) {

        String errorCode = e.getFieldError().getDefaultMessage();

        ExceptionErrorCode code = ExceptionErrorCode.valueOf(errorCode);

        ExceptionDto exceptionDto = new ExceptionDto(code.getStatus().value(), code.getMessage());
        return ResponseEntity.status(code.getStatus()).body(exceptionDto);
    }
}
