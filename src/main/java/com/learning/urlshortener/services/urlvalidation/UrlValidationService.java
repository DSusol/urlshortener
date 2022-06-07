package com.learning.urlshortener.services.urlvalidation;

import com.learning.urlshortener.domain.Customer;

public interface UrlValidationService {

    UrlValidationResult getUrlValidationResultFor(Customer customer, String url);
}