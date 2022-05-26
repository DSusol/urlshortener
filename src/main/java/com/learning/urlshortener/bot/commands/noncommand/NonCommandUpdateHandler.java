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
    private final HelpHandler helpHandler;
    private final StartHandler startHandler;

    public NonCommandUpdateHandler(@Lazy AbsSender bot, MessageHandler messageHandler,
                                   HelpHandler helpHandler, StartHandler startHandler) {
        this.bot = bot;
        this.messageHandler = messageHandler;
        this.helpHandler = helpHandler;
        this.startHandler = startHandler;
    }

    @SneakyThrows
    public void handleUpdate(Update update) {

        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        Message message = update.getMessage();
        if (message.getText().equals("/start")) {
            startHandler.saveNewCustomer(message.getChatId());
            bot.execute(startHandler.getWelcomeMessage(message));
            return;
        }

        if (message.getText().equals("/help")) {
            bot.execute(helpHandler.getHelpMessage(message));
            return;
        }

        SendMessage sendMessage = messageHandler.prepareSendMessage(message, "bot.default.message");
        bot.execute(sendMessage);
    }
}
