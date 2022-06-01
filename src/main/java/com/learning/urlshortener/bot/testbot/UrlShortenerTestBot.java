package com.learning.urlshortener.bot.testbot;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import com.learning.urlshortener.bot.UrlShortenerBot;
import com.learning.urlshortener.bot.commands.noncommand.NonCommandUpdateHandler;
import com.learning.urlshortener.bot.utils.MessageHandler;
import com.learning.urlshortener.bot.utils.TgIncomingUpdateLogger;

@Component
@Profile("test")
public class UrlShortenerTestBot extends UrlShortenerBot {

    private final ExecutedTgTestMethodsRegistry executedTgTestMethodsRegistry;

    @Autowired
    public UrlShortenerTestBot(List<IBotCommand> sortedBotCommands, TgIncomingUpdateLogger logger,
                               MessageHandler messageHandler, NonCommandUpdateHandler nonCommandUpdateHandler,
                               ExecutedTgTestMethodsRegistry executedTgTestMethodsRegistry) {
        super(sortedBotCommands, logger, messageHandler, nonCommandUpdateHandler);
        this.executedTgTestMethodsRegistry = executedTgTestMethodsRegistry;
    }

    @Override
    public <T extends Serializable, Method extends BotApiMethod<T>> T execute(Method method) {
        executedTgTestMethodsRegistry.handleMethod(method);
        return null;
    }
}