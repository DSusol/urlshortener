package com.learning.urlshortener.bot;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.learning.urlshortener.bot.utils.MessageUtils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UpdateFailureProcessor {

    private final UrlShortenerBot urlShortenerBot;
    private final MessageUtils messageUtils;

    public UpdateFailureProcessor(@Lazy UrlShortenerBot urlShortenerBot, MessageUtils messageUtils) {
        this.urlShortenerBot = urlShortenerBot;
        this.messageUtils = messageUtils;
    }

    @SneakyThrows
    public void handleFailedUpdate(Update update, Exception exception) {
        log.warn("Handling exception for update {}", update, exception);
        urlShortenerBot.execute(messageUtils.prepareSendMessage(update.getMessage(), "default.exception.message"));
    }
}