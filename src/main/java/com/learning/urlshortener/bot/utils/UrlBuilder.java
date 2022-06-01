package com.learning.urlshortener.bot.utils;

import static org.apache.commons.lang3.StringUtils.appendIfMissing;

import org.springframework.stereotype.Component;

import com.learning.urlshortener.bot.utils.domain.DomainProvider;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UrlBuilder {

    private final DomainProvider domainProvider;

    public String buildUrlWithDomain(String token) {
        String domain = domainProvider.getDomain();
        appendIfMissing(domain, "/");
        return domain + token;
    }
}
