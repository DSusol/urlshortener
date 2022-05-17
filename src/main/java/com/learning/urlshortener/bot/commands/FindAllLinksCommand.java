package com.learning.urlshortener.bot.commands;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import com.learning.urlshortener.bot.MessageCourier;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@Order(3)
@AllArgsConstructor
@Component
public class FindAllLinksCommand implements IBotCommand {

    private static final String FIND_ALL_LINKS_IDENTIFIER = "my_links";

    private final MessageCourier messageCourier;

    @Override
    public String getCommandIdentifier() {
        return FIND_ALL_LINKS_IDENTIFIER;
    }

    @Override
    public String getDescription() {
        return messageCourier.getCommandDescription("find.all.links.command.description");
    }

    @SneakyThrows
    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        //todo: implement link list provision
        messageCourier.sendCommandResponse(absSender, message, "find.all.links.command.response");
    }
}