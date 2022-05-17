package com.learning.urlshortener.bot;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class InternationalizedMessenger {

    private Locale messageLocale;
    private final MessageSource messageSource;

    InternationalizedMessenger(MessageSource messageSource) {
        this.messageSource = messageSource;
        this.messageLocale = Locale.getDefault();
    }

    public String getMessageFor(String template) {
        return messageSource.getMessage(template, null, messageLocale);
    }

    public synchronized String getMessageFor(String template, String languageCode) {
        messageLocale = Locale.forLanguageTag(languageCode);
        return getMessageFor(template);
    }
}
