package com.learning.urlshortener.bot.utils;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageHandler {

    SendMessage prepareSendMessage(Message message, String template);

    String getI18nMessageFor(String template, String languageCode);
}
