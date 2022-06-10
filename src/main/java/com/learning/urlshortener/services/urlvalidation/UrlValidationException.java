package com.learning.urlshortener.services.urlvalidation;

import lombok.Getter;

@Getter
public class UrlValidationException extends Exception {

    private final UrlValidationExceptionCause urlValidationExceptionCause;

    public UrlValidationException(UrlValidationExceptionCause urlValidationExceptionCause, String message) {
        super(message);
        this.urlValidationExceptionCause = urlValidationExceptionCause;
    }
}