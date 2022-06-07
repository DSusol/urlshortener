package com.learning.urlshortener.services.urlvalidation.validators;

import com.learning.urlshortener.services.urlvalidation.UrlValidationResult;

public interface UrlValidator {

    boolean isNotValid(String url);

    UrlValidationResult getUrlValidationStatus();
}
