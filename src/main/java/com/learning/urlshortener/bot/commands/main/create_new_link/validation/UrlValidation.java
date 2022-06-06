package com.learning.urlshortener.bot.commands.main.create_new_link.validation;

import static com.learning.urlshortener.bot.commands.main.create_new_link.validation.UrlValidationStatus.VALID;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UrlValidation {

    private final List<UValidator> urlValidators;

    public UrlValidationStatus getUrlValidationStatusFor(String url) {
        return urlValidators.stream()
                .filter(urlValidator -> urlValidator.isNotValid(url))
                .map(UValidator::getUrlValidationStatus)
                .findFirst()
                .orElse(VALID);
    }
}
