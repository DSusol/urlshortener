package com.learning.urlshortener.bot.utils;

import java.util.Locale;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageHandler {

    SendMessage prepareSendMessage(Message message, String template);

    String getI18nMessageFor(String template);

    Locale resolveMessageLocale(Message message);
}
