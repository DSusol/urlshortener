package com.learning.urlshortener.bot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import com.learning.urlshortener.bot.commands.NonCommandUpdateHandler;
import com.learning.urlshortener.bot.logs.TgIncomingUpdateLogger;
import com.learning.urlshortener.bot.utils.MessageHandler;

import lombok.Getter;

@Component
@Profile("test")
public class UrlShortenerTestBot extends UrlShortenerBot {

    @Getter
    private final List<BotApiMethod<?>> executedMethods = new ArrayList<>();

    @Autowired
    public UrlShortenerTestBot(NonCommandUpdateHandler nonCommandUpdateHandler, List<IBotCommand> sortedBotCommands,
                               MessageHandler messageHandler, TgIncomingUpdateLogger logger) {
        super(nonCommandUpdateHandler, sortedBotCommands, messageHandler, logger);
    }

    @Override
    public <T extends Serializable, Method extends BotApiMethod<T>> T execute(Method method) {
        executedMethods.add(method);
        return null;
    }
}