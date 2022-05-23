package com.learning.urlshortener.bot.commands;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import com.learning.urlshortener.bot.utils.MessageHandler;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@Order(4)
@AllArgsConstructor
@Component
class DeleteLinkCommand implements IBotCommand {

    private final MessageHandler messageHandler;

    @Override
    public String getCommandIdentifier() {
        return "delete_link";
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