package com.learning.urlshortener.bot.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;

import com.learning.urlshortener.bot.state.StateHandler;
import com.learning.urlshortener.bot.utils.MessageHandler;
import com.learning.urlshortener.services.BotServices;

public abstract class AbstractCommand implements IBotCommand {

    @Autowired
    protected BotServices botServices;

    @Autowired
    protected StateHandler stateHandler;

    @Autowired
    protected MessageHandler messageHandler;

    public enum Command {
        DEFAULT,
        NEW_LINK,
        SHOW_LINK,
        MY_LINKS,
        DELETE_LINK
    }
}
