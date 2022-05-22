package com.learning.urlshortener.bot.commands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.learning.urlshortener.bot.api.TgApiExecutor;
import com.learning.urlshortener.bot.utils.MessageHandler;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class NonCommandUpdateHandler {

    private final TgApiExecutor apiExecutor;
    private final MessageHandler messageHandler;
    private final HelpHandler helpHandler;

    public void handleUpdate(Update update) {

        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        Message message = update.getMessage();
        if (!message.getText().equals("/help")) {
            SendMessage sendMessage = messageHandler.prepareSendMessage(message, "bot.default.message");
            apiExecutor.executeSendMessage(sendMessage);
            return;
        }

        helpHandler.getHelpMessage(message);
    }
}
