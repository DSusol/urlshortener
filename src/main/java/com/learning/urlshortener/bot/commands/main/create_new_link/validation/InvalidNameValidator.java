package com.learning.urlshortener.bot.commands.main.create_new_link.validation;

import static com.learning.urlshortener.bot.commands.main.create_new_link.validation.UrlValidationStatus.INVALID_NAME;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Order(1)
@Component
@RequiredArgsConstructor
class InvalidNameValidator implements UValidator {

    private final UrlValidator urlValidator;

    @Override
    public boolean isNotValid(String url) {
        return !urlValidator.isValid(url);
    }

    @Override
    public UrlValidationStatus getUrlValidationStatus() {
        return INVALID_NAME;
    }
}
