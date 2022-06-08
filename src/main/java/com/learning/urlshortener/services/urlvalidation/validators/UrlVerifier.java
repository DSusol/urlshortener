package com.learning.urlshortener.services.urlvalidation.validators;

public interface UrlVerifier {

    void validate(String url) throws UrlValidationException;
}