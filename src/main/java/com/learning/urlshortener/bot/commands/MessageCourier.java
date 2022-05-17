package com.learning.urlshortener.bot.commands;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
@Component
class MessageCourier {

    private final MessageSource messageSource;

    @SneakyThrows
    void sendMessage(AbsSender absSender, Message message, String template) {

        String chatId = message.getChatId().toString();
        Locale customerLocale = Locale.forLanguageTag(message.getFrom().getLanguageCode());
        String responseMessage = messageSource.getMessage(template, null, customerLocale);

        absSender.execute(new SendMessage(chatId, responseMessage));
    }
//
//    String getCommandDescription(String template) {
//        return messageSource.getMessage(template)
//    }
}
