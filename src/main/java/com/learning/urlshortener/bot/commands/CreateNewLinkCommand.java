package com.learning.urlshortener.bot.commands;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.urlshortener.bot.InternationalizedMessenger;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Order(1)
@Slf4j
@AllArgsConstructor
@Component
public class CreateNewLinkCommand implements IBotCommand {

    private static final String CREATE_NEW_LINK_IDENTIFIER = "new_link";

    private final InternationalizedMessenger messenger;

    @Override
    public String getCommandIdentifier() {
        return CREATE_NEW_LINK_IDENTIFIER;
    }

    @Override
    public String getDescription() {
        return messenger.getMessageFor("new.link.command.description");
    }

    @SneakyThrows
    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        log.debug(new ObjectMapper().writeValueAsString(message));

        //todo: implement new link creation
        String languageCode = message.getFrom().getLanguageCode();

        absSender.execute(new SendMessage(
                message.getChatId().toString(),
                messenger.getMessageFor("new.link.command.response", languageCode)));
    }
}