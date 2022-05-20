package com.learning.urlshortener.bot.commands;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import com.learning.urlshortener.bot.InternationalizedMessenger;
import com.learning.urlshortener.bot.api.TgApiExecutor;
import com.learning.urlshortener.bot.logs.Logger;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@Order(1)
@AllArgsConstructor
@Component
class CreateNewLinkCommand implements IBotCommand {

    private static final String CREATE_NEW_LINK_IDENTIFIER = "new_link";

    private final TgApiExecutor apiExecutor;
    private final InternationalizedMessenger messenger;
    private final Logger logger;

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
        logger.logRequest(message);

        //todo: implement new link creation
        String languageCode = message.getFrom().getLanguageCode();

        apiExecutor.executeSendMessage(absSender, new SendMessage(
                message.getChatId().toString(),
                messenger.getMessageFor("new.link.command.response", languageCode)));
    }
}