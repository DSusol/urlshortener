package com.learning.urlshortener.bot.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.telegram.telegrambots.meta.bots.AbsSender;

import com.learning.urlshortener.bot.commands.main.CommandExecutor;
import com.learning.urlshortener.bot.utils.MessageHandler;
import com.learning.urlshortener.services.BotServices;

public abstract class AbstractCommandExecutor implements CommandExecutor {

    @Autowired
    protected BotServices botServices;

    @Autowired
    protected MessageHandler messageHandler;

    protected final AbsSender bot;

    @Autowired
    protected AbstractCommandExecutor(@Lazy AbsSender bot) {
        this.bot = bot;
    }
}
