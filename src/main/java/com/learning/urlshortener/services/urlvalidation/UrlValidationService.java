package com.learning.urlshortener.services.urlvalidation;

public interface UrlValidationService {

    UrlValidationResult getUrlValidationResultFor(String url);
}