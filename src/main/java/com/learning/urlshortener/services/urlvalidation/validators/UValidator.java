package com.learning.urlshortener.services.urlvalidation.validators;

import com.learning.urlshortener.services.urlvalidation.UrlValidationResult;

public interface UValidator {

    boolean isNotValid(String url);

    UrlValidationResult getUrlValidationStatus();
}
