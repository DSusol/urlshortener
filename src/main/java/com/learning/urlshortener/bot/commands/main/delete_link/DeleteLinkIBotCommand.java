package com.learning.urlshortener.bot.commands.main.delete_link;

import static com.learning.urlshortener.bot.commands.CommandType.DELETE_LINK;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import com.learning.urlshortener.bot.commands.AbstractIBotCommand;
import com.learning.urlshortener.bot.commands.main.MainMenuCommands;

import lombok.SneakyThrows;

@Order(4)
@MainMenuCommands
@Component
class DeleteLinkIBotCommand extends AbstractIBotCommand {

    @Override
    public String getCommandIdentifier() {
        return DELETE_LINK.getCommandIdentifier();
    }

    @Override
    public String getDescription() {
        return "delete.links.command.description";
    }

    @SneakyThrows
    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        //todo: implement link removal
        absSender.execute(messageUtils.prepareSendMessage(message, "delete.links.command.response"));
    }
}