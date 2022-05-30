package com.learning.urlshortener.bot.commands.main.find_all_links;

import static com.learning.urlshortener.bot.commands.Command.MY_LINKS;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import com.learning.urlshortener.bot.commands.AbstractCommand;

import lombok.SneakyThrows;

@Order(3)
@Qualifier("MainMenuCommands")
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