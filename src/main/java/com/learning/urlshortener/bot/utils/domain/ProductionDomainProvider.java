package com.learning.urlshortener.bot.utils.domain;

import org.springframework.context.annotation.Profile;
import org.springframework.core.env.PropertyResolver;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Profile("prod")
public class ProductionDomainProvider implements DomainProvider {

    private final PropertyResolver propertyResolver;

    @Override
    public String getDomain() {
        return propertyResolver.getProperty("base.domain");
    }
}