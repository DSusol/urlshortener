package com.learning.urlshortener.bot.utils.message;

import java.util.Locale;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageHandler {

    SendMessage prepareSendMessage(Message message, String template);

    SendMessage prepareSendMessage(Long chatId, String template);

    String getI18nMessageFor(String template);

    Locale resolveMessageLocale(Message message);
}
