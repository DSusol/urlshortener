package com.learning.urlshortener.bot;

import java.util.List;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.learning.urlshortener.bot.commands.NonCommandUpdateHandler;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

@Component
public class UrlShortenerBot extends TelegramLongPollingCommandBot {

    @Setter
    private String botUserName;

    @Setter
    private String botToken;

    @Getter
    private final List<IBotCommand> sortedBotCommands;

    private final NonCommandUpdateHandler nonCommandUpdateHandler;

    @SneakyThrows
    public UrlShortenerBot(
            NonCommandUpdateHandler nonCommandUpdateHandler,
            List<IBotCommand> sortedBotCommands) {
        this.nonCommandUpdateHandler = nonCommandUpdateHandler;
        this.sortedBotCommands = sortedBotCommands;

        sortedBotCommands.forEach(this::register);
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
    public void processNonCommandUpdate(Update update) {
        nonCommandUpdateHandler.handleUpdate(this, update);
    }
}