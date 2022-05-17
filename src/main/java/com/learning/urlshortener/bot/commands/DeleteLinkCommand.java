package com.learning.urlshortener.bot.commands;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import com.learning.urlshortener.bot.MessageCourier;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@Order(4)
@AllArgsConstructor
@Component
public class DeleteLinkCommand implements IBotCommand {

    private static final String DELETE_LINK_IDENTIFIER = "delete_link";

    private final MessageCourier messageCourier;

    @Override
    public String getCommandIdentifier() {
        return DELETE_LINK_IDENTIFIER;
    }

    @Override
    public String getDescription() {
        return messageCourier.getCommandDescription("delete.links.command.description");
    }

    @SneakyThrows
    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        //todo: implement link removal
        messageCourier.sendCommandResponse(absSender, message, "delete.links.command.response");
    }
}