package com.learning.urlshortener.bot.logs;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@AllArgsConstructor
public class TgIncomingUpdateLogger {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    public void logIncomingUpdate(Update updateObject) {
        log.info("Request object: {}", objectMapper.writeValueAsString(updateObject));
    }
}