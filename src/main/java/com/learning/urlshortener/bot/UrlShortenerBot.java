package com.learning.urlshortener.bot;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.learning.urlshortener.bot.commands.NonCommandUpdateHandler;
import com.learning.urlshortener.bot.logs.TgIncomingUpdateLogger;

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

    private final TgIncomingUpdateLogger logger;
    private final NonCommandUpdateHandler nonCommandUpdateHandler;

    @SneakyThrows
    public UrlShortenerBot(
            @Value("${telegram-bot.name}") String botUserName,
            @Value("${telegram-bot.token}") String botToken,
            NonCommandUpdateHandler nonCommandUpdateHandler,
            List<IBotCommand> sortedBotCommands,
            TgIncomingUpdateLogger logger) {
        this.botUserName = botUserName;
        this.botToken = botToken;
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
        updates.forEach(logger::logIncomingUpdate);
        super.onUpdatesReceived(updates);
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        nonCommandUpdateHandler.handleUpdate(update);
    }
}