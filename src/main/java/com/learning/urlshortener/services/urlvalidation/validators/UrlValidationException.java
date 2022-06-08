package com.learning.urlshortener.services.urlvalidation.validators;

public abstract class UrlValidationException extends Exception {

    public UrlValidationException(String message) {
        super(message);
    }
}