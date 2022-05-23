package com.learning.urlshortener.bot.commands;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import com.learning.urlshortener.bot.utils.MessageHandler;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@Order(3)
@AllArgsConstructor
@Component
class FindAllLinksCommand implements IBotCommand {

    private final MessageHandler messageHandler;

    @Override
    public String getCommandIdentifier() {
        return "my_links";
    }

    @Override
    public String getDescription() {
        return "find.all.links.command.description";
    }

    @SneakyThrows
    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        //todo: implement link list provision
        absSender.execute(messageHandler.prepareSendMessage(message, "find.all.links.command.response"));
    }
}