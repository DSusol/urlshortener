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
class MessageHandlerImpl implements MessageHandler {

    private final MessageSource messageSource;

    @Override
    public SendMessage prepareSendMessage(Message message, String template) {
        String languageCode = message.getFrom().getLanguageCode();
        String messageText = getI18nMessageFor(template, languageCode);
        return new SendMessage(message.getChatId().toString(), messageText);
    }

    @Override
    public String getI18nMessageFor(String template, String languageCode) {
        LocaleContextHolder.setLocale(Locale.forLanguageTag(languageCode));
        String i18nMessage = messageSource.getMessage(template, null, LocaleContextHolder.getLocale());
        LocaleContextHolder.resetLocaleContext();
        return i18nMessage;
    }
}
