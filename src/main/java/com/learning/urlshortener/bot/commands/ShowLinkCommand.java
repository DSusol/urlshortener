package com.learning.urlshortener.bot.commands;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import com.learning.urlshortener.bot.MessageCourier;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@Order(2)
@AllArgsConstructor
@Component
public class ShowLinkCommand implements IBotCommand {

    private static final String SHOW_LINK_IDENTIFIER = "show_link";

    private final MessageCourier messageCourier;

    @Override
    public String getCommandIdentifier() {
        return SHOW_LINK_IDENTIFIER;
    }

    @Override
    public String getDescription() {
        return messageCourier.getCommandDescription("show.link.command.description");
    }

    @SneakyThrows
    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        //todo: implement link details provision
        messageCourier.sendCommandResponse(absSender, message, "show.link.command.response");
    }
}