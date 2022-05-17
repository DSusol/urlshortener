package com.learning.urlshortener.bot.commands;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import com.learning.urlshortener.bot.InternationalizedMessenger;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@Order(4)
@AllArgsConstructor
@Component
public class DeleteLinkCommand implements IBotCommand {

    private static final String DELETE_LINK_IDENTIFIER = "delete_link";

    private final InternationalizedMessenger messenger;

    @Override
    public String getCommandIdentifier() {
        return DELETE_LINK_IDENTIFIER;
    }

    @Override
    public String getDescription() {
        return messenger.getMessageFor("delete.links.command.description");
    }

    @SneakyThrows
    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        //todo: implement link removal
        String languageCode = message.getFrom().getLanguageCode();

        absSender.execute(new SendMessage(
                message.getChatId().toString(),
                messenger.getMessageFor("delete.links.command.response", languageCode)));
    }
}