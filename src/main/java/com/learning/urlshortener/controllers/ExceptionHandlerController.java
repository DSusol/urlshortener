package com.learning.urlshortener.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ExceptionHandlerController {

    @SneakyThrows
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleDefaultException(Exception exception) {
        log.info("Bad request handling: " + exception.getMessage());
        Map<String, Object> errorInfo = new HashMap<>();
        errorInfo.put("message", exception.getMessage());
        errorInfo.put("status", HttpStatus.BAD_REQUEST);
        errorInfo.put("status_code", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
}