package com.learning.urlshortener.services.urlvalidation;

import java.util.List;

import org.springframework.stereotype.Component;

import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.services.urlvalidation.validators.UrlValidationException;
import com.learning.urlshortener.services.urlvalidation.validators.UrlVerifier;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UrlValidation {

    private final List<UrlVerifier> urlVerifiers;

    public void validateUrlFor(Customer customer, String url) throws UrlValidationException {
        for(UrlVerifier urlVerifier: urlVerifiers) {
            urlVerifier.validate(url);
        }
    }
}