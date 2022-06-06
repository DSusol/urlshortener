package com.learning.urlshortener.bot.config;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public UrlValidator urlValidator() {
        return new UrlValidator();
    }
}