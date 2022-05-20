package com.learning.urlshortener.bot.commands;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.urlshortener.bot.InternationalizedMessenger;
import com.learning.urlshortener.bot.api.TgApiExecutor;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Order(2)
@Slf4j
@AllArgsConstructor
@Component
public class ShowLinkCommand implements IBotCommand {

    private static final String SHOW_LINK_IDENTIFIER = "show_link";

    private final TgApiExecutor executor;
    private final InternationalizedMessenger messenger;

    @Override
    public String getCommandIdentifier() {
        return SHOW_LINK_IDENTIFIER;
    }

    @Override
    public String getDescription() {
        return messenger.getMessageFor("show.link.command.description");
    }

    @SneakyThrows
    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        log.debug(new ObjectMapper().writeValueAsString(message));

        //todo: implement link details provision
        String languageCode = message.getFrom().getLanguageCode();

        executor.executeSendMessage(absSender, new SendMessage(
                message.getChatId().toString(),
                messenger.getMessageFor("show.link.command.response", languageCode)));
    }
}