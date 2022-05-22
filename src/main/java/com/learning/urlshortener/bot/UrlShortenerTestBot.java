package com.learning.urlshortener.bot;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import com.learning.urlshortener.bot.commands.NonCommandUpdateHandler;
import com.learning.urlshortener.bot.logs.Logger;

import lombok.Getter;

@Component
@Profile("test")
public class UrlShortenerTestBot extends UrlShortenerBot {

    @Getter
    private final Set<BotApiMethod<?>> methods = new HashSet<>();

    public UrlShortenerTestBot(NonCommandUpdateHandler nonCommandUpdateHandler,
                               List<IBotCommand> sortedBotCommands,
                               Logger logger) {
        super(nonCommandUpdateHandler, sortedBotCommands, logger);
    }

    @Override
    public <T extends Serializable, Method extends BotApiMethod<T>> T execute(Method method) {
        methods.add(method);
        return null;
    }
}