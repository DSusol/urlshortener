package com.learning.urlshortener.bot.commands.noncommand;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.learning.urlshortener.bot.UrlShortenerBot;
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
        log.error("Handling exception for update id {} : {}", update.getUpdateId(), exception.getMessage());
        String defaultErrorResponse = messageUtils.getI18nMessageFor("default.exception.message");
        urlShortenerBot.execute(new SendMessage(
                update.getMessage().getChatId().toString(),
                defaultErrorResponse + exception.getMessage()
        ));
    }
}