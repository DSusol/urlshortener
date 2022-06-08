package com.learning.urlshortener.services.urlvalidation.validators;

public class UrlLengthValidationException extends UrlValidationException {

    public UrlLengthValidationException(String message) {
        super(message);
    }
}