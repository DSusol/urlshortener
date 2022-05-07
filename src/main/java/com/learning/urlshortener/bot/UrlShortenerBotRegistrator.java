package com.learning.urlshortener.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import lombok.SneakyThrows;

@Component
@Profile("!test")
public class UrlShortenerBotRegistrator extends TelegramLongPollingBot {

    private final String botUserName;
    private final String botToken;

    @SneakyThrows
    public UrlShortenerBotRegistrator(
            @Value("${telegram-bot.name}") String botUserName,
            @Value("${telegram-bot.token}") String botToken,
            TelegramBotsApi telegramBotsApi) {
        this.botUserName = botUserName;
        this.botToken = botToken;
        telegramBotsApi.registerBot(this);
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        //todo: delegate to services (update handler)
        //temporary method implementation:
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(update.getMessage().getText());
            execute(message);
        }
    }
}
