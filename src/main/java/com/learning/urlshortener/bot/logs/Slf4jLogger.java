package com.learning.urlshortener.bot.logs;

import java.text.MessageFormat;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@AllArgsConstructor
public class Slf4jLogger implements Logger {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public void logRequest(Object object) {
        log.info(MessageFormat.format("Request: Thread: {0}, request object: {1}",
                Thread.currentThread().getId(), objectMapper.writeValueAsString(object)));
    }
}
