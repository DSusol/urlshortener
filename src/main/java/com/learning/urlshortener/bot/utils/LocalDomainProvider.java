package com.learning.urlshortener.bot.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Profile("!prod")
public class LocalDomainProvider implements DomainProvider {

    @Value("${base.domain}")
    private String baseDomain;

    private final ServletWebServerApplicationContext webServerAppContext;

    @Override
    public String getDomain() {
        return baseDomain + webServerAppContext.getWebServer().getPort() + "/";
    }
}