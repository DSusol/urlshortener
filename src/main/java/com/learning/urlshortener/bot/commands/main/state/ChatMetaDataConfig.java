package com.learning.urlshortener.bot.commands.main.state;

import java.util.function.Function;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ChatMetaDataConfig {

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public ChatMetaData chatMetaData(Long chatId) {
        return new ChatMetaData(chatId);
    }

    @Bean
    public Function<Long, ChatMetaData> metaDataProvider() {
        return this::chatMetaData;
    }
}