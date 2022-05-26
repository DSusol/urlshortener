package com.learning.urlshortener.bot.commands;

import static com.learning.urlshortener.bot.commands.Command.DELETE_LINK;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import lombok.SneakyThrows;

@Order(4)
@Component
class DeleteLinkCommand extends AbstractCommand {

    @Override
    public String getCommandIdentifier() {
        return DELETE_LINK.name().toLowerCase();
    }

    @Override
    public String getDescription() {
        return "delete.links.command.description";
    }

    @SneakyThrows
    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        //todo: implement link removal
        absSender.execute(messageHandler.prepareSendMessage(message, "delete.links.command.response"));
    }
}