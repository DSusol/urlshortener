package com.learning.urlshortener.services.urlvalidation.validators;

import static com.learning.urlshortener.services.urlvalidation.exceptions.UrlExceptionCause.INVALID_SYNTAX;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.learning.urlshortener.services.urlvalidation.UrlValidators;
import com.learning.urlshortener.services.urlvalidation.exceptions.UrlValidationException;

import lombok.RequiredArgsConstructor;

@Order(1)
@Component
@RequiredArgsConstructor
class UrlSyntaxValidator implements UrlValidators {

    private static final UrlValidator urlValidator = new UrlValidator();

    @Override
    public void validate(String url) throws UrlValidationException {
        if (!urlValidator.isValid(url)) {
            throw new UrlValidationException(INVALID_SYNTAX, "Invalid url syntax.");
        }
    }
}