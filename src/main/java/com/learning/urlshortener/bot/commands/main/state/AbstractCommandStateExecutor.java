package com.learning.urlshortener.bot.commands.main.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.telegram.telegrambots.meta.bots.AbsSender;

import com.learning.urlshortener.bot.utils.MessageUtils;
import com.learning.urlshortener.services.UrlShortenerService;

public abstract class AbstractCommandStateExecutor {

    @Autowired
    protected UrlShortenerService urlShortenerService;

    @Autowired
    protected MessageUtils messageUtils;

    protected AbsSender bot;

    @Autowired
    private void setBot(@Lazy AbsSender bot) {
        this.bot = bot;
    }

    public abstract CommandState getCommandState();

    public abstract boolean executeStateAndReturnCommandFinishStatus(ChatMetaData metaData);
}
