package com.learning.urlshortener.bot.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;

import com.learning.urlshortener.bot.state.MultiStepCommandHandler;
import com.learning.urlshortener.bot.utils.MessageHandler;
import com.learning.urlshortener.services.BotServices;

public abstract class AbstractCommand implements IBotCommand {

    @Autowired
    protected BotServices botServices;

    @Autowired
    protected MultiStepCommandHandler multiStepCommandHandler;

    @Autowired
    protected MessageHandler messageHandler;

    public enum Command {
        DEFAULT,
        START,
        HELP,
        NEW_LINK,
        SHOW_LINK,
        MY_LINKS,
        DELETE_LINK
    }
}
