package com.learning.urlshortener.bot.commands.noncommand;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.learning.urlshortener.bot.utils.MessageHandler;
import com.learning.urlshortener.services.BotServices;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class StartHandler {

    private final BotServices botServices;
    private final MessageHandler messageHandler;

    public void saveNewCustomer(Long chatId) {
        if (botServices.getCustomerByChatId(chatId) == null) {
            botServices.saveNewCustomer(chatId);
        }
    }

    public SendMessage getWelcomeMessage(Message message) {
        return messageHandler.prepareSendMessage(message, "bot.welcome.message");
    }
}
