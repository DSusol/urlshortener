package com.learning.urlshortener.bot.commands;

import static com.learning.urlshortener.bot.commands.Command.SHOW_LINK;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import lombok.SneakyThrows;

@Order(2)
@Component
class ShowLinkCommand extends AbstractCommand {

    @Override
    public String getCommandIdentifier() {
        return SHOW_LINK.name().toLowerCase();
    }

    @Override
    public String getDescription() {
        return "show.link.command.description";
    }

    @SneakyThrows
    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        //todo: implement link details provision
        absSender.execute(messageHandler.prepareSendMessage(message, "show.link.command.response"));
    }
}