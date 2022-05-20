package com.learning.urlshortener.bot;

import java.util.List;
import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UrlShortenerBot extends TelegramLongPollingCommandBot {

    @Setter
    private String botUserName;

    @Setter
    private String botToken;

    private final List<IBotCommand> sortedBotCommands;
    private final InternationalizedMessenger messenger;

    @SneakyThrows
    public UrlShortenerBot(
            List<IBotCommand> sortedBotCommands,
            InternationalizedMessenger messenger) {
        this.messenger = messenger;
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
        log.debug(new ObjectMapper().writeValueAsString(update));

        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        String chatId = update.getMessage().getChatId().toString();
        String languageCode = update.getMessage().getFrom().getLanguageCode();

        if (!update.getMessage().getText().equals("/help")) {
            execute(new SendMessage(chatId, messenger.getMessageFor("bot.default.message", languageCode)));
            return;
        }

        StringBuilder helpMessageBuilder = new StringBuilder();

        helpMessageBuilder.append(messenger.getMessageFor("bot.help.header", languageCode));
        LocaleContextHolder.setLocale(Locale.forLanguageTag(languageCode));
        sortedBotCommands.forEach(cmd -> helpMessageBuilder.append("/").append(cmd.getCommandIdentifier())
                .append(" - ").append(cmd.getDescription()).append("\n"));
        LocaleContextHolder.resetLocaleContext();

        SendMessage sendMessage = new SendMessage(chatId, helpMessageBuilder.toString());
        sendMessage.enableHtml(true);
        execute(sendMessage);
    }
}