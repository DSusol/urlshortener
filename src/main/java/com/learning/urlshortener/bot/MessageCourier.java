package com.learning.urlshortener.bot;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import lombok.SneakyThrows;

@Component
public class MessageCourier {

    private Locale messageLocale;
    private final MessageSource messageSource;

    MessageCourier(MessageSource messageSource) {
        this.messageSource = messageSource;
        this.messageLocale = Locale.getDefault();
    }

    @SneakyThrows
    public void sendCommandResponse(AbsSender absSender, Message message, String template) {

        String chatId = message.getChatId().toString();
        messageLocale = Locale.forLanguageTag(message.getFrom().getLanguageCode());
        String responseMessage = messageSource.getMessage(template, null, messageLocale);

        absSender.execute(new SendMessage(chatId, responseMessage));
    }

    public String getCommandDescription(String template) {
        return messageSource.getMessage(template, null, messageLocale);
    }
}
