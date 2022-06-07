package com.learning.urlshortener.services.urlvalidation.validators;

import static com.learning.urlshortener.services.urlvalidation.UrlValidationResult.INVALID;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.learning.urlshortener.services.urlvalidation.UrlValidationResult;

import lombok.RequiredArgsConstructor;

@Order(1)
@Component
@RequiredArgsConstructor
class InvalidNameValidator implements UrlValidator {

    private static final org.apache.commons.validator.routines.UrlValidator urlValidator
            = new org.apache.commons.validator.routines.UrlValidator();

    @Override
    public boolean isNotValid(String url) {
        return !urlValidator.isValid(url);
    }

    @Override
    public UrlValidationResult getUrlValidationStatus() {
        return INVALID;
    }
}
