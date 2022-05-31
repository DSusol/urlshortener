package com.learning.urlshortener.bot.commands.main.create_new_link;

import static com.learning.urlshortener.bot.commands.Command.DEFAULT;
import static com.learning.urlshortener.bot.commands.Command.NEW_LINK;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;

import com.learning.urlshortener.bot.commands.AbstractCommandExecutor;
import com.learning.urlshortener.bot.commands.Command;
import com.learning.urlshortener.bot.commands.main.state.ChatMetaData;
import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;

import lombok.SneakyThrows;

@Component
public class CreateNewLinkCommandExecutor extends AbstractCommandExecutor {

    private final Command command = NEW_LINK;

    protected CreateNewLinkCommandExecutor(@Lazy AbsSender bot) {
        super(bot);
    }

    @Override
    public Command getExecutorCommand() {
        return command;
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

        metaData.setCommand(DEFAULT);
    }
}
