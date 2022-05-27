package com.learning.urlshortener.bot.commands;

import static com.learning.urlshortener.bot.commands.AbstractCommand.Command.DEFAULT;
import static com.learning.urlshortener.bot.commands.AbstractCommand.Command.NEW_LINK;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;

import lombok.SneakyThrows;

@Order(1)
@Component
class CreateNewLinkCommand extends AbstractCommand {

    @Override
    public String getCommandIdentifier() {
        return NEW_LINK.name().toLowerCase();
    }

    @Override
    public String getDescription() {
        return "new.link.command.description";
    }

    @SneakyThrows
    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        Long chatId = message.getChatId();

        if (arguments.length == 0) {
            stateHandler.setChatState(chatId, NEW_LINK);
            absSender.execute(messageHandler.prepareSendMessage(message, "new.link.command.request.url"));
            return;
        }

        stateHandler.setChatState(chatId, DEFAULT);

        String url = arguments[0];
        Customer customer = botServices.getCustomerByChatId(chatId);
        Link newLink = botServices.saveNewLink(customer, url);

        SendMessage sMessage = new SendMessage(chatId.toString(),
                messageHandler.getI18nMessageFor("new.link.command.response")
                        + botServices.getShortenedUrlByToken(newLink.getToken()));

        absSender.execute(sMessage);
    }
}