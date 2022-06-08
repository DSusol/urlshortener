package com.learning.urlshortener.services.urlvalidation;

public interface UrlValidators {

    void validate(String url) throws UrlValidationException;
}