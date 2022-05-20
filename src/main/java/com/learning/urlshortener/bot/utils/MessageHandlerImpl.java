package com.learning.urlshortener.bot.utils;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.learning.urlshortener.bot.InternationalizedMessenger;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
class MessageHandlerImpl implements MessageHandler {

    private final InternationalizedMessenger messenger;

    @Override
    public SendMessage prepareSendMessage(Message message, String template) {
        String languageCode = message.getFrom().getLanguageCode();
        String chatId = message.getChatId().toString();
        String messageText = messenger.getMessageFor("new.link.command.response", languageCode);
        return new SendMessage(chatId, messageText);
    }
}
