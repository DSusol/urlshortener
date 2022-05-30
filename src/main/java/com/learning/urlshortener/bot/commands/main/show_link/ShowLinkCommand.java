package com.learning.urlshortener.bot.commands.main.show_link;

import static com.learning.urlshortener.bot.commands.Command.SHOW_LINK;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import com.learning.urlshortener.bot.commands.AbstractCommand;

import lombok.SneakyThrows;

@Order(2)
@Qualifier("MainMenuCommands")
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