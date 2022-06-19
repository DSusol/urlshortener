package com.learning.urlshortener.bot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.learning.urlshortener.bot.UrlShortenerBot;
import com.learning.urlshortener.bot.utils.WebHookSetter;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Configuration
@RequiredArgsConstructor
@Profile("!test")
public class BotConfig {

    @Value("${telegram-bot.receive.update.mode}")
    private String receiveUpdateOption;

    private final WebHookSetter webHookSetter;
    private final UrlShortenerBot bot;

    @SneakyThrows
    @Bean
    void telegramBotRegistration() {
        if (receiveUpdateOption.equals("polling")) {
            new TelegramBotsApi(DefaultBotSession.class).registerBot(bot);
            return;
        }

        if (receiveUpdateOption.equals("webhook")) {
            webHookSetter.setWebHookFor(bot.getBotToken());
            return;
        }

        throw new IllegalArgumentException("Invalid telegram-bot.receive.update.mode. Check application properties. Available options: polling/webhook.");
    }
}