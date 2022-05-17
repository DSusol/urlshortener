package com.learning.urlshortener.bot;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import lombok.SneakyThrows;

@Component
public class MessageCourier {

    private Locale messageLocale;
    private final MessageSource messageSource;

    MessageCourier(MessageSource messageSource) {
        this.messageSource = messageSource;
        this.messageLocale = Locale.getDefault();
    }

    public SendMessage getDefaultBotResponse(Update update) {
        locateSetUp(update);
        String responseMessage = messageSource.getMessage("bot.default.message", null, messageLocale);

        String chatId = update.getMessage().getChatId().toString();
        return new SendMessage(chatId, responseMessage);
    }

    public String getHelpHeader(Update update) {
        locateSetUp(update);
        return messageSource.getMessage("bot.help.header", null, messageLocale);
    }

    @SneakyThrows
    public void sendCommandResponse(AbsSender absSender, Message message, String template) {

        locateSetUp(message);
        String chatId = message.getChatId().toString();
        String responseMessage = messageSource.getMessage(template, null, messageLocale);

        absSender.execute(new SendMessage(chatId, responseMessage));
    }

    public String getCommandDescription(String template) {
        return messageSource.getMessage(template, null, messageLocale);
    }

    private void locateSetUp(Message message) {
        messageLocale = Locale.forLanguageTag(message.getFrom().getLanguageCode());
    }

    private void locateSetUp(Update update) {
        locateSetUp(update.getMessage());
    }
}
