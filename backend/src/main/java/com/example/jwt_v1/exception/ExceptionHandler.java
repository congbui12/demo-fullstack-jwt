package com.example.jwt_v1.exception;

import com.example.jwt_v1.dto.MessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = AppException.class)
    @ResponseBody
    public ResponseEntity<MessageDto> handleException(AppException e) {
        return ResponseEntity.status(e.getStatus()).body(new MessageDto(e.getMessage()));
    }
}
