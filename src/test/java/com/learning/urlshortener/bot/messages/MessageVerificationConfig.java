package com.learning.urlshortener.bot.messages;

import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.learning.urlshortener.bot",
        "org.springframework.boot.autoconfigure.context",
        "org.springframework.boot.autoconfigure.jackson"})
public class MessageVerificationConfig {
}