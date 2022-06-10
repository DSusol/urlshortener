package com.learning.urlshortener.services.urlvalidation;

import com.learning.urlshortener.services.urlvalidation.exceptions.UrlValidationException;

public interface UrlValidators {

    void validate(String url) throws UrlValidationException;
}