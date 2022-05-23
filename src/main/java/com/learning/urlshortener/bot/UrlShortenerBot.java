package com.learning.urlshortener.bot;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.learning.urlshortener.bot.commands.NonCommandUpdateHandler;
import com.learning.urlshortener.bot.logs.TgIncomingUpdateLogger;
import com.learning.urlshortener.bot.utils.MessageHandler;

import lombok.Getter;

@Component
@Profile("!test")
public class UrlShortenerBot extends TelegramLongPollingCommandBot {

    @Value("${telegram-bot.name}")
    private String botUserName;

    @Value("${telegram-bot.token}")
    private String botToken;

    @Getter
    private final List<IBotCommand> sortedBotCommands;

    private final TgIncomingUpdateLogger logger;
    private final MessageHandler messageHandler;
    private final NonCommandUpdateHandler nonCommandUpdateHandler;

    @Autowired
    public UrlShortenerBot(
            NonCommandUpdateHandler nonCommandUpdateHandler,
            List<IBotCommand> sortedBotCommands,
            MessageHandler messageHandler,
            TgIncomingUpdateLogger logger) {
        this.nonCommandUpdateHandler = nonCommandUpdateHandler;
        this.messageHandler = messageHandler;
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
        for (Update update : updates) {
            logger.logIncomingUpdate(update);
            try {
                LocaleContextHolder.setLocale(update.hasMessage()
                        ? messageHandler.resolveMessageLocale(update.getMessage())
                        : Locale.getDefault());
                this.onUpdateReceived(update);
            } finally {
                LocaleContextHolder.resetLocaleContext();
            }
        }
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        nonCommandUpdateHandler.handleUpdate(update);
    }
}