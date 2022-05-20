package com.learning.urlshortener.bot;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class InternationalizedMessenger {

    private final MessageSource messageSource;

    InternationalizedMessenger(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessageFor(String template) {
        return messageSource.getMessage(template, null, LocaleContextHolder.getLocale());
    }

    public String getMessageFor(String template, String languageCode) {
        log.debug("Current thread id: " + Thread.currentThread().getId());

        LocaleContextHolder.setLocale(Locale.forLanguageTag(languageCode));

        try {
            return getMessageFor(template);
        } finally {
            LocaleContextHolder.resetLocaleContext();
        }
    }
}
