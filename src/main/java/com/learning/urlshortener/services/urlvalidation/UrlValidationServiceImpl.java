package com.learning.urlshortener.services.urlvalidation;

import static com.learning.urlshortener.services.urlvalidation.UrlValidationResult.VALID;

import java.util.List;

import org.springframework.stereotype.Component;

import com.learning.urlshortener.services.urlvalidation.validators.UrlValidator;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UrlValidationServiceImpl implements UrlValidationService {

    private final List<UrlValidator> urlValidators;

    @Override
    public UrlValidationResult getUrlValidationResultFor(String url) {
        return urlValidators.stream()
                .filter(urlValidator -> urlValidator.isNotValid(url))
                .map(UrlValidator::getUrlValidationStatus)
                .findFirst()
                .orElse(VALID);
    }
}