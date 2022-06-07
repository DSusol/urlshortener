package com.learning.urlshortener.bot.commands.main.show_link;

import static com.learning.urlshortener.bot.commands.CommandType.SHOW_LINK;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import com.learning.urlshortener.bot.commands.AbstractIBotCommand;
import com.learning.urlshortener.bot.commands.main.MainMenuCommands;

import lombok.SneakyThrows;

@Order(2)
@MainMenuCommands
@Component
class ShowLinkIBotCommand extends AbstractIBotCommand {

    @Override
    public String getCommandIdentifier() {
        return SHOW_LINK.getCommandIdentifier();
    }

    @Override
    public String getDescription() {
        return "show.link.command.description";
    }

    @SneakyThrows
    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        //todo: implement link details provision
        absSender.execute(messageUtils.prepareSendMessage(message, "show.link.command.response"));
    }
}