package com.learning.urlshortener.bot.commands.main.find_all_links;

import static com.learning.urlshortener.bot.commands.CommandType.MY_LINKS;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import com.learning.urlshortener.bot.commands.AbstractIBotCommand;
import com.learning.urlshortener.bot.commands.main.MainMenuCommands;

import lombok.SneakyThrows;

@Order(3)
@MainMenuCommands
@Component
class FindAllLinksIBotCommand extends AbstractIBotCommand {

    @Override
    public String getCommandIdentifier() {
        return MY_LINKS.getCommandIdentifier();
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