package com.learning.urlshortener.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.learning.urlshortener.bot.commands.CreateNewLinkCommand;
import com.learning.urlshortener.bot.commands.DeleteLinkCommand;
import com.learning.urlshortener.bot.commands.FindAllLinksCommand;
import com.learning.urlshortener.bot.commands.HelpCommand;
import com.learning.urlshortener.bot.commands.ShowLinkCommand;

import lombok.SneakyThrows;

@Component
@Profile("!test")
public class UrlShortenerBotRegistrator extends TelegramLongPollingCommandBot {

    private final String botUserName;
    private final String botToken;

    @SneakyThrows
    public UrlShortenerBotRegistrator(
            @Value("${telegram-bot.name}") String botUserName,
            @Value("${telegram-bot.token}") String botToken,
            CreateNewLinkCommand createNewLinkCommand,
            ShowLinkCommand showLinkCommand,
            FindAllLinksCommand findAllLinksCommand,
            DeleteLinkCommand deleteLinkCommand,
            TelegramBotsApi telegramBotsApi) {
        this.botUserName = botUserName;
        this.botToken = botToken;
        registerAll(createNewLinkCommand, showLinkCommand, findAllLinksCommand, deleteLinkCommand);
        register(new HelpCommand(this));
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
        if (update.hasMessage() && update.getMessage().hasText()) {
            execute(SendMessage.builder()
                    .chatId(update.getMessage().getChatId().toString())
                    .text("The command is not recognized. See /help for available options.")
                    .build());
        }
    }
}