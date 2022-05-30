package com.learning.urlshortener.bot.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;

import com.learning.urlshortener.bot.commands.main.state.MultiStepCommandHandler;
import com.learning.urlshortener.bot.utils.MessageHandler;

public abstract class AbstractCommand implements IBotCommand {

    @Autowired
    protected MultiStepCommandHandler multiStepCommandHandler;

    @Autowired
    protected MessageHandler messageHandler;
}
