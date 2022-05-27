package com.learning.urlshortener.bot.commands.noncommand;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.learning.urlshortener.bot.utils.MessageHandler;

import lombok.AllArgsConstructor;

// change to Command using qualifier
@Component
@AllArgsConstructor
public class StartHandler {

    private final MessageHandler messageHandler;

    public SendMessage getWelcomeMessage(Message message) {
        assert 1 == 2; // temp breakpoint
        return messageHandler.prepareSendMessage(message, "bot.welcome.message");
    }
}
