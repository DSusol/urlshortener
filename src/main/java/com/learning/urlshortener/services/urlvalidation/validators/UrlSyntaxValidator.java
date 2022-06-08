package com.learning.urlshortener.services.urlvalidation.validators;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.learning.urlshortener.services.urlvalidation.UrlValidators;

import lombok.RequiredArgsConstructor;

@Order(1)
@Component
@RequiredArgsConstructor
class UrlSyntaxValidator implements UrlValidators {

    private static final UrlValidator urlValidator = new UrlValidator();

    @Override
    public void validate(String url) throws UrlSyntaxValidationException {
        if(!urlValidator.isValid(url)) {
            throw new UrlSyntaxValidationException("Invalid url syntax.");
        }
    }
}