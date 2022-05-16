package com.learning.urlshortener.bot.commands;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@Order(2)
@AllArgsConstructor
@Component
public class ShowLinkCommand implements IBotCommand {

    private static final String SHOW_LINK_IDENTIFIER = "show_link";
    private static final String SHOW_LINK_DESCRIPTION = "show link details";

    @Override
    public String getCommandIdentifier() {
        return SHOW_LINK_IDENTIFIER;
    }

    @Override
    public String getDescription() {
        return SHOW_LINK_DESCRIPTION;
    }

    @SneakyThrows
    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        //temporary implementation:
        SendMessage sendMessage = SendMessage.builder()
                .chatId(message.getChatId().toString())
                .text("will show link details")
                .build();
        absSender.execute(sendMessage);
    }
}