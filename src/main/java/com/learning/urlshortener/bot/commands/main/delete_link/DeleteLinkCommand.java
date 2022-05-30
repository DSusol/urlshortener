package com.learning.urlshortener.bot.commands.main.delete_link;

import static com.learning.urlshortener.bot.commands.Command.DELETE_LINK;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import com.learning.urlshortener.bot.commands.AbstractCommand;

import lombok.SneakyThrows;

@Order(4)
@Qualifier("MainMenuCommands")
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