package com.learning.urlshortener.bot.utils.domain;

import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.PropertyResolver;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Profile("!prod")
public class LocalDomainProvider implements DomainProvider {

    private final PropertyResolver propertyResolver;
    private final ServletWebServerApplicationContext webServerAppContext;

    @Override
    public String getDomain() {
        return propertyResolver.getProperty("base.domain")
                + webServerAppContext.getWebServer().getPort() + "/";
    }
}