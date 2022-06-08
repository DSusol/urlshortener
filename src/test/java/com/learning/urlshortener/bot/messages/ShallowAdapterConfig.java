package com.learning.urlshortener.bot.messages;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.learning.urlshortener.services.UrlShortenerService;
import com.learning.urlshortener.services.urlvalidation.UrlValidation;

@WebMvcTest
@ComponentScan(basePackages = "com.learning.urlshortener.bot")
public class ShallowAdapterConfig {

    @MockBean
    ServletWebServerApplicationContext servletWebServerApplicationContext;

    @MockBean
    UrlValidation urlValidation;

    @MockBean
    UrlShortenerService urlShortenerService;
}