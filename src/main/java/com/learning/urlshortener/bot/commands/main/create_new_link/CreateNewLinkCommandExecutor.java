package com.learning.urlshortener.bot.commands.main.create_new_link;

import static com.learning.urlshortener.bot.commands.CommandType.DEFAULT;
import static com.learning.urlshortener.bot.commands.CommandType.NEW_LINK;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import com.learning.urlshortener.bot.commands.CommandType;
import com.learning.urlshortener.bot.commands.main.AbstractCommandExecutor;
import com.learning.urlshortener.bot.commands.main.state.ChatMetaData;
import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Component
@RequiredArgsConstructor
public class CreateNewLinkCommandExecutor extends AbstractCommandExecutor {

    private final UrlValidator urlValidator;

    @Override
    public CommandType getExecutorCommand() {
        return NEW_LINK;
    }

    @SneakyThrows
    @Override
    public void execute(ChatMetaData metaData) {
        String url = metaData.getMessage();
        Long chatId = metaData.getChatId();

        if(!urlValidator.isValid(url)) {
            bot.execute(messageHandler.prepareSendMessage(chatId, "new.link.command.invalid.url"));
            return;
        }

        Customer customer = urlShortenerService.getOrCreateCustomerByChatId(chatId);
        Link newLink = urlShortenerService.saveNewLink(customer, url);

        SendMessage sMessage = new SendMessage(chatId.toString(),
                messageHandler.getI18nMessageFor("new.link.command.response")
                        + urlBuilder.buildUrlWithDomain(newLink.getToken()));

        bot.execute(sMessage);

        metaData.setCommandType(DEFAULT);
    }
}