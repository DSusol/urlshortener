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

@Order(3)
@AllArgsConstructor
@Component
public class FindAllLinksCommand implements IBotCommand {

    private static final String FIND_ALL_LINKS_IDENTIFIER = "my_links";

    private final TgApiExecutor apiExecutor;
    private final InternationalizedMessenger messenger;
    private final Logger logger;

    @Override
    public String getCommandIdentifier() {
        return FIND_ALL_LINKS_IDENTIFIER;
    }

    @Override
    public String getDescription() {
        return messenger.getMessageFor("find.all.links.command.description");
    }

    @SneakyThrows
    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        logger.logRequest(message);

        //todo: implement link list provision
        String languageCode = message.getFrom().getLanguageCode();

        apiExecutor.executeSendMessage(absSender, new SendMessage(
                message.getChatId().toString(),
                messenger.getMessageFor("find.all.links.command.response", languageCode)));
    }
}