package com.example.junit.web.handler;

import com.example.junit.web.dto.response.CMResDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.example.junit.web")
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> apiException(RuntimeException e) {
        log.info("advice : {}", e.getMessage());
        return new ResponseEntity<>(CMResDto.builder()
                .code(-1)
                .msg(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
