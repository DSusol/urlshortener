package com.learning.urlshortener.services.urlvalidation.validators;

import static com.learning.urlshortener.services.urlvalidation.UrlValidationResult.INVALID;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.learning.urlshortener.services.urlvalidation.UrlValidationResult;

import lombok.RequiredArgsConstructor;

@Order(1)
@Component
@RequiredArgsConstructor
class InvalidNameValidator implements UValidator {

    private static final UrlValidator urlValidator = new UrlValidator();

    @Override
    public boolean isNotValid(String url) {
        return !urlValidator.isValid(url);
    }

    @Override
    public UrlValidationResult getUrlValidationStatus() {
        return INVALID;
    }
}
