package com.learning.urlshortener.services.urlvalidation;

import static com.learning.urlshortener.services.UrlShortenerServiceImpl.URL_TOKEN_LENGTH;
import static com.learning.urlshortener.services.urlvalidation.UrlValidationExceptionCause.INVALID_SYNTAX;
import static com.learning.urlshortener.services.urlvalidation.UrlValidationExceptionCause.SHORT_LENGTH;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Component;

import com.learning.urlshortener.bot.utils.domain.DomainProvider;
import com.learning.urlshortener.domain.Customer;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UrlValidation {

    private static final UrlValidator urlValidator = new UrlValidator();
    private final DomainProvider domainProvider;

    //todo: incorporate customer maximum allowed link count validation
    public void validateUrlFor(Customer customer, String url) throws UrlValidationException {
        validateUrlSyntax(url);
        validateUrlLength(url);
    }

    public void validateUrlSyntax(String url) throws UrlValidationException {
        if (!urlValidator.isValid(url)) {
            throw new UrlValidationException(INVALID_SYNTAX, "Invalid url syntax.");
        }
    }

    public void validateUrlLength(String url) throws UrlValidationException {
        String domainName = domainProvider.getDomain();
        if (url.length() <= (domainName.length() + URL_TOKEN_LENGTH)) {
            throw new UrlValidationException(SHORT_LENGTH, "Url cannot be shortened.");
        }
    }
}