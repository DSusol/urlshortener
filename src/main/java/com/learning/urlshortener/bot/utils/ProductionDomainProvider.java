package com.learning.urlshortener.bot.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class ProductionDomainProvider implements DomainProvider {

    @Value("${base.domain}")
    private String baseDomain;

    @Override
    public String getDomain() {
        return baseDomain;
    }
}