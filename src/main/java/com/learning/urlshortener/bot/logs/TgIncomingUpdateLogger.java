package com.learning.urlshortener.bot.logs;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface TgIncomingUpdateLogger {
    void logIncomingUpdate(Update update);
}