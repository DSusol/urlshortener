package com.learning.urlshortener.bot.commands;

import static com.learning.urlshortener.bot.commands.AbstractCommand.Command.MY_LINKS;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import lombok.SneakyThrows;

@Order(3)
@Component
class FindAllLinksCommand extends AbstractCommand {

    @Override
    public String getCommandIdentifier() {
        return MY_LINKS.name().toLowerCase();
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