package com.learning.urlshortener.bot.commands;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import com.learning.urlshortener.bot.utils.MessageHandler;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@Order(1)
@AllArgsConstructor
@Component
class CreateNewLinkCommand implements IBotCommand {

    private final MessageHandler messageHandler;

    @Override
    public String getCommandIdentifier() {
        return "new_link";
    }

    @Override
    public String getDescription() {
        return "new.link.command.description";
    }

    @SneakyThrows
    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        //todo: implement new link creation
        absSender.execute(messageHandler.prepareSendMessage(message, "new.link.command.response"));
    }
}