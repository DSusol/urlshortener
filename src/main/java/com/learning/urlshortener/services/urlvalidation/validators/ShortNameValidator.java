package com.learning.urlshortener.services.urlvalidation.validators;

import static com.learning.urlshortener.services.urlvalidation.UrlValidationResult.SHORT_NAME;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.learning.urlshortener.bot.utils.domain.DomainProvider;
import com.learning.urlshortener.services.urlvalidation.UrlValidationResult;

import lombok.RequiredArgsConstructor;

@Order(2)
@Component
@RequiredArgsConstructor
class ShortNameValidator implements UrlValidator {

    private final int tokenLength = 6;
    private final DomainProvider domainProvider;

    @Override
    public boolean isNotValid(String url) {
        String domainName = domainProvider.getDomain();
        return url.length() <= (domainName.length() + tokenLength);
    }

    @Override
    public UrlValidationResult getUrlValidationStatus() {
        return SHORT_NAME;
    }
}
