package com.learning.urlshortener.bot.commands.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.telegram.telegrambots.meta.bots.AbsSender;

import com.learning.urlshortener.bot.utils.UrlBuilder;
import com.learning.urlshortener.bot.utils.message.MessageHandler;
import com.learning.urlshortener.services.urlshortener.UrlShortenerService;

public abstract class AbstractCommandExecutor implements CommandExecutor {

    @Autowired
    protected UrlShortenerService urlShortenerService;

    @Autowired
    protected MessageHandler messageHandler;

    @Autowired
    protected UrlBuilder urlBuilder;

    protected AbsSender bot;

    @Autowired
    private void setBot(@Lazy AbsSender bot) {
        this.bot = bot;
    }
}
