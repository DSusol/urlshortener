package com.learning.urlshortener.bot;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UrlShortenerBotRegistrator extends TelegramLongPollingCommandBot {

    private final String botUserName;
    private final String botToken;
    private final List<IBotCommand> sortedBotCommands;

    private final InternationalizedMessenger messenger;

    @SneakyThrows
    public UrlShortenerBotRegistrator(
            @Value("${telegram-bot.name}") String botUserName,
            @Value("${telegram-bot.token}") String botToken,
            List<IBotCommand> sortedBotCommands,
            InternationalizedMessenger messenger,
            TelegramBotsApi telegramBotsApi) {
        this.botUserName = botUserName;
        this.botToken = botToken;
        this.messenger = messenger;
        this.sortedBotCommands = sortedBotCommands;
        sortedBotCommands.forEach(this::register);

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

        // synchronizing command descriptions for specific languageCode
        synchronized (messenger) {
            helpMessageBuilder.append(messenger.getMessageFor("bot.help.header", languageCode));
            sortedBotCommands.forEach(cmd -> helpMessageBuilder.append("/").append(cmd.getCommandIdentifier())
                    .append(" - ").append(cmd.getDescription()).append("\n"));
        }

        SendMessage sendMessage = new SendMessage(chatId, helpMessageBuilder.toString());
        sendMessage.enableHtml(true);
        execute(sendMessage);
    }
}