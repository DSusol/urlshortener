package com.learning.urlshortener.bot.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.learning.urlshortener.bot.utils.MessageHandler;
import com.learning.urlshortener.bot.utils.MessageHandlerImpl;

@ComponentScan(basePackages = {
//        "com.learning.urlshortener.bot.utils",
        "org.springframework.boot.autoconfigure.context"
//        "org.springframework.boot.autoconfigure.jackson"
})
public class MessageVerificationConfig {

//    @Bean
//    JacksonAutoConfiguration objectMapper() {
//        return new JacksonAutoConfiguration();
//    }
//
//    @Bean
//    MessageSourceAutoConfiguration messageSource() {
//        return new MessageSourceAutoConfiguration();
//    }

    @Bean
    MessageHandler messageHandler(MessageSource messageSource) {
        return new MessageHandlerImpl(messageSource);
    }
}