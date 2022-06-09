package com.learning.urlshortener.services.urlvalidation.exceptions;

import com.learning.urlshortener.services.urlvalidation.UrlValidationException;

public class UrlLengthValidationException extends UrlValidationException {

    public UrlLengthValidationException(String message) {
        super(message);
    }
}