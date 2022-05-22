package com.learning.urlshortener.bot.api;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface TgApiExecutor {

    void executeSendMessage(SendMessage sendMessage);
}