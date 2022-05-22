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
class TgIncomingUpdateLoggerImpl implements TgIncomingUpdateLogger {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public void logIncomingUpdate(Update update) {
        log.info("Request object: {}", objectMapper.writeValueAsString(update));
    }
}