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
public class RestExceptionHandler {

    @SneakyThrows
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleDefaultException(Exception exception) {
        log.warn("Bad request handling.", exception);
        return getMapResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @SneakyThrows
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundException(Exception exception) {
        log.warn("Not found request handling.", exception);
        return getMapResponseEntity(HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<Map<String, Object>> getMapResponseEntity(HttpStatus httpStatus) {
        Map<String, Object> errorInfo = new HashMap<>();
        errorInfo.put("status", httpStatus);
        errorInfo.put("status_code", httpStatus.value());
        return new ResponseEntity<>(errorInfo, httpStatus);
    }
}