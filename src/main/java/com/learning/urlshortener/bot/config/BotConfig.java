package com.learning.urlshortener.bot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.learning.urlshortener.bot.UrlShortenerBot;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@Configuration
@AllArgsConstructor
@Profile("!test")
public class BotConfig {

    private final UrlShortenerBot urlShortenerBot;

    @SneakyThrows
    @Bean
    TelegramBotsApi telegramBotRegistrator() {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(urlShortenerBot);
        return botsApi;
    }
}