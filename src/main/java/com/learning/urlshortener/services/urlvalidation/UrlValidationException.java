package com.learning.urlshortener.services.urlvalidation;

public abstract class UrlValidationException extends Exception {

    public UrlValidationException(String message) {
        super(message);
    }
}