package com.learning.urlshortener.services.urlvalidation;

import static com.learning.urlshortener.services.urlvalidation.UrlValidationResult.VALID;

import java.util.List;

import org.springframework.stereotype.Service;

import com.learning.urlshortener.services.urlvalidation.validators.UrlVerifier;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UrlValidationServiceImpl implements UrlValidationService {

    private final List<UrlVerifier> urlVerifiers;

    @Override
    public UrlValidationResult getUrlValidationResultFor(String url) {
        return urlVerifiers.stream()
                .filter(urlVerifier -> urlVerifier.isNotValid(url))
                .map(UrlVerifier::getUrlValidationStatus)
                .findFirst()
                .orElse(VALID);
    }
}