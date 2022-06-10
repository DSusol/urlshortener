package com.learning.urlshortener.services.urlvalidation.exceptions;

import lombok.Getter;

@Getter
public class UrlValidationException extends Exception {

    private final UrlExceptionCause urlExceptionCause;

    public UrlValidationException(UrlExceptionCause urlExceptionCause, String message) {
        super(message);
        this.urlExceptionCause = urlExceptionCause;
    }
}