package com.learning.urlshortener.bot.commands;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@Order(3)
@AllArgsConstructor
@Component
public class FindAllLinksCommand implements IBotCommand {

    private static final String FIND_ALL_LINKS_IDENTIFIER = "my_links";
    private static final String FIND_ALL_LINKS_DESCRIPTION = "show all links";

    @Override
    public String getCommandIdentifier() {
        return FIND_ALL_LINKS_IDENTIFIER;
    }

    @Override
    public String getDescription() {
        return FIND_ALL_LINKS_DESCRIPTION;
    }

    @SneakyThrows
    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        //temporary implementation:
        absSender.execute(new SendMessage(message.getChatId().toString(), "will show all links"));
    }
}