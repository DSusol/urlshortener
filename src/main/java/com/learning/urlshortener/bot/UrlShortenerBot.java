package com.learning.urlshortener.bot;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.learning.urlshortener.bot.commands.NonCommandUpdateHandler;
import com.learning.urlshortener.bot.logs.Logger;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

@Component
@Profile("!test")
public class UrlShortenerBot extends TelegramLongPollingCommandBot {

    @Setter
    private String botUserName;

    @Setter
    private String botToken;

    @Getter
    private final List<IBotCommand> sortedBotCommands;

    private final Logger logger;
    private final NonCommandUpdateHandler nonCommandUpdateHandler;

    @SneakyThrows
    public UrlShortenerBot(
            NonCommandUpdateHandler nonCommandUpdateHandler,
            List<IBotCommand> sortedBotCommands,
            Logger logger) {
        this.nonCommandUpdateHandler = nonCommandUpdateHandler;
        this.sortedBotCommands = sortedBotCommands;
        this.logger = logger;

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

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        updates.forEach(logger::logRequest);
        super.onUpdatesReceived(updates);
    }

    @SneakyThrows
    @Override
    public void processNonCommandUpdate(Update update) {
        nonCommandUpdateHandler.handleUpdate(update);
    }
}