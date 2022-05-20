package com.learning.urlshortener.bot.api;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;

public interface TgApiExecutor {

    void executeSendMessage(AbsSender bot, SendMessage sendMessage);
}
