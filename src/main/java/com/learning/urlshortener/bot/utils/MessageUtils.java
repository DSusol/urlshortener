package com.learning.urlshortener.bot.utils;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MessageUtils {

    private final MessageSource messageSource;

    public SendMessage prepareSendMessage(Message message, String template) {
        return prepareSendMessage(message.getChatId(), template);
    }

    public SendMessage prepareSendMessage(Long chatId, String template) {
        String messageText = getI18nMessageFor(template);
        return new SendMessage(chatId.toString(), messageText);
    }

    public String getI18nMessageFor(String template) {
        return messageSource.getMessage(template, null, LocaleContextHolder.getLocale());
    }

    public Locale resolveMessageLocale(Message message) {
        return Locale.forLanguageTag(message.getFrom().getLanguageCode());
    }
}
