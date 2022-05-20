package com.learning.urlshortener.bot.config;

import org.mapstruct.BeanMapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.urlshortener.bot.UrlShortenerBot;

import lombok.SneakyThrows;

@Configuration
@Profile("!test")
public class AppConfig {

    private final String botUserName;
    private final String botToken;
    private final UrlShortenerBot urlShortenerBot;

    public AppConfig(@Value("${telegram-bot.name}") String botUserName,
                     @Value("${telegram-bot.token}") String botToken,
                     UrlShortenerBot urlShortenerBot) {
        this.botUserName = botUserName;
        this.botToken = botToken;
        this.urlShortenerBot = urlShortenerBot;
    }

    @SneakyThrows
    @Bean
    TelegramBotsApi telegramBotRegistrator() {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        urlShortenerBot.setBotUserName(botUserName);
        urlShortenerBot.setBotToken(botToken);
        botsApi.registerBot(urlShortenerBot);
        return botsApi;
    }
}