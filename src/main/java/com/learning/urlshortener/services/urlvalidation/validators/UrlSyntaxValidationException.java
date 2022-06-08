package com.learning.urlshortener.services.urlvalidation.validators;

import com.learning.urlshortener.services.urlvalidation.UrlValidationException;

public class UrlSyntaxValidationException extends UrlValidationException {

    public UrlSyntaxValidationException(String message) {
        super(message);
    }
}
