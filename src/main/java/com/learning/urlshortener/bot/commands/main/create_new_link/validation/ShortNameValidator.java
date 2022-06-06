package com.learning.urlshortener.bot.commands.main.create_new_link.validation;

import static com.learning.urlshortener.bot.commands.main.create_new_link.validation.UrlValidationStatus.SHORT_NAME;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.learning.urlshortener.bot.utils.domain.DomainProvider;

import lombok.RequiredArgsConstructor;

@Order(2)
@Component
@RequiredArgsConstructor
class ShortNameValidator implements UValidator {

    private final DomainProvider domainProvider;

    @Override
    public boolean isNotValid(String url) {
        final int tokenLength = 6;
        String domainName = domainProvider.getDomain();
        return url.length() <= (domainName.length() + tokenLength);
    }

    @Override
    public UrlValidationStatus getUrlValidationStatus() {
        return SHORT_NAME;
    }
}
