package com.learning.urlshortener.services.urlvalidation.validators;

import static com.learning.urlshortener.services.UrlShortenerServiceImpl.URL_TOKEN_LENGTH;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.learning.urlshortener.bot.utils.domain.DomainProvider;
import com.learning.urlshortener.services.urlvalidation.UrlValidators;

import lombok.RequiredArgsConstructor;

@Order(2)
@Component
@RequiredArgsConstructor
class UrlLengthValidator implements UrlValidators {

    private final DomainProvider domainProvider;

    @Override
    public void validate(String url) throws UrlLengthValidationException {
        String domainName = domainProvider.getDomain();
        if(url.length() <= (domainName.length() + URL_TOKEN_LENGTH)) {
            throw new UrlLengthValidationException("Url cannot be shortened.");
        }
    }
}