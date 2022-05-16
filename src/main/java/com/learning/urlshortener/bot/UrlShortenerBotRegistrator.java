package com.learning.urlshortener.bot;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import lombok.SneakyThrows;

@Component
@Profile("!test")
public class UrlShortenerBotRegistrator extends TelegramLongPollingCommandBot {

    private final String botUserName;
    private final String botToken;
    private final List<IBotCommand> sortedBotCommands;

    @SneakyThrows
    public UrlShortenerBotRegistrator(
            @Value("${telegram-bot.name}") String botUserName,
            @Value("${telegram-bot.token}") String botToken,
            List<IBotCommand> sortedBotCommands,
            TelegramBotsApi telegramBotsApi) {
        this.botUserName = botUserName;
        this.botToken = botToken;
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
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        String chatId = update.getMessage().getChatId().toString();
        if (!update.getMessage().getText().equals("/help")) {
            execute(new SendMessage(chatId, "The command is not recognized. See /help for available options."));
            return;
        }

        StringBuilder helpMessageBuilder = new StringBuilder("<b>Available commands:\n</b>");
        sortedBotCommands.forEach(cmd -> helpMessageBuilder.append("/").append(cmd.getCommandIdentifier())
                .append(" - ").append(cmd.getDescription()).append("\n"));

        SendMessage sendMessage = new SendMessage(chatId, helpMessageBuilder.toString());
        sendMessage.enableHtml(true);
        execute(sendMessage);
    }
}