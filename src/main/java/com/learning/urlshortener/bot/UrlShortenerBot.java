package com.learning.urlshortener.bot;

import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.learning.urlshortener.bot.commands.noncommand.NonCommandUpdateHandler;
import com.learning.urlshortener.bot.utils.MessageUtils;
import com.learning.urlshortener.bot.utils.TgIncomingUpdateLogger;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Profile("!test")
public class UrlShortenerBot extends TelegramLongPollingCommandBot {

    @Value("${telegram-bot.name}")
    private String botUserName;

    @Value("${telegram-bot.token}")
    private String botToken;

    private final List<IBotCommand> sortedBotCommands;
    private final TgIncomingUpdateLogger logger;
    private final MessageUtils messageUtils;
    private final NonCommandUpdateHandler nonCommandUpdateHandler;
    private final UpdateFailureProcessor updateFailureProcessor;

    @PostConstruct
    void registerCommands() {
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
                        ? messageUtils.resolveMessageLocale(update.getMessage())
                        : Locale.getDefault());
                this.onUpdateReceived(update);
            } catch (Exception exception) {
                updateFailureProcessor.handleFailedUpdate(update, exception);
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