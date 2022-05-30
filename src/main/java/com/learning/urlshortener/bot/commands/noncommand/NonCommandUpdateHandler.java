package com.learning.urlshortener.bot.commands.noncommand;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import com.learning.urlshortener.bot.utils.MessageHandler;

import lombok.SneakyThrows;

@Component
public class NonCommandUpdateHandler {

    private final AbsSender bot;
    private final MessageHandler messageHandler;

    public NonCommandUpdateHandler(@Lazy AbsSender bot, MessageHandler messageHandler) {
        this.bot = bot;
        this.messageHandler = messageHandler;
    }

    @SneakyThrows
    public void handleUpdate(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        Message message = update.getMessage();
        SendMessage sendMessage = messageHandler.prepareSendMessage(message, "bot.default.message");
        bot.execute(sendMessage);
    }
}
