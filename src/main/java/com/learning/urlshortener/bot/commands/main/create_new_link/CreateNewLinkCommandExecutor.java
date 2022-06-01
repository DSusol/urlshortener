package com.learning.urlshortener.bot.commands.main.create_new_link;

import static com.learning.urlshortener.bot.commands.CommandType.DEFAULT;
import static com.learning.urlshortener.bot.commands.CommandType.NEW_LINK;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;

import com.learning.urlshortener.bot.commands.CommandType;
import com.learning.urlshortener.bot.commands.main.AbstractCommandExecutor;
import com.learning.urlshortener.bot.commands.main.state.ChatMetaData;
import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;

import lombok.SneakyThrows;

@Component
public class CreateNewLinkCommandExecutor extends AbstractCommandExecutor {

    private final CommandType commandType = NEW_LINK;

    @Override
    public CommandType getExecutorCommand() {
        return commandType;
    }

    @SneakyThrows
    @Override
    public void execute(ChatMetaData metaData) {
        String url = metaData.getMessage();
        Long chatId = metaData.getChatId();

        Customer customer = urlShortenerService.getOrCreateCustomerByChatId(chatId);
        Link newLink = urlShortenerService.saveNewLink(customer, url);

        SendMessage sMessage = new SendMessage(chatId.toString(),
                messageHandler.getI18nMessageFor("new.link.command.response")
                        + urlShortenerService.getShortenedUrlByToken(newLink.getToken()));

        bot.execute(sMessage);

        metaData.setCommandType(DEFAULT);
    }
}