package com.learning.urlshortener.services.urlvalidation.exceptions;

import com.learning.urlshortener.services.urlvalidation.UrlValidationException;

public class ExistingUrlException extends UrlValidationException {

    public ExistingUrlException(String s) {
        super(s);
    }
}