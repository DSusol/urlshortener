package com.learning.urlshortener.services.urlvalidation;

import java.util.List;

import org.springframework.stereotype.Component;

import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.services.urlvalidation.exceptions.UrlValidationException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UrlValidation {

    private final List<UrlValidators> urlValidators;

    //todo: incorporate customer maximum allowed link count validation
    public void validateUrlFor(Customer customer, String url) throws UrlValidationException {
        for (UrlValidators urlValidators : this.urlValidators) {
            urlValidators.validate(url);
        }
    }
}