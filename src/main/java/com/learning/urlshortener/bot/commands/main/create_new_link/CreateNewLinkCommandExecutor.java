package com.learning.urlshortener.bot.commands.main.create_new_link;

import static com.learning.urlshortener.bot.commands.CommandType.DEFAULT;
import static com.learning.urlshortener.bot.commands.CommandType.NEW_LINK;
import static com.learning.urlshortener.services.urlvalidation.UrlValidationResult.INVALID;
import static com.learning.urlshortener.services.urlvalidation.UrlValidationResult.SHORT_NAME;
import static com.learning.urlshortener.services.urlvalidation.UrlValidationResult.VALID;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import com.learning.urlshortener.bot.commands.CommandType;
import com.learning.urlshortener.bot.commands.main.AbstractCommandExecutor;
import com.learning.urlshortener.services.urlvalidation.UrlValidationService;
import com.learning.urlshortener.services.urlvalidation.UrlValidationResult;
import com.learning.urlshortener.bot.commands.main.state.ChatMetaData;
import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Component
@RequiredArgsConstructor
public class CreateNewLinkCommandExecutor extends AbstractCommandExecutor {

    private final UrlValidationService urlValidationService;

    @Override
    public CommandType getExecutorCommand() {
        return NEW_LINK;
    }

    @SneakyThrows
    @Override
    public void execute(ChatMetaData metaData) {
        Long chatId = metaData.getChatId();
        String url = metaData.getMessage();
        UrlValidationResult urlValidationResult = urlValidationService.getUrlValidationResultFor(url);

        if(urlValidationResult == INVALID) {
            bot.execute(messageUtils.prepareSendMessage(chatId, "new.link.command.invalid.url"));
            return;
        }

        if(urlValidationResult == SHORT_NAME) {
            metaData.setCommandType(DEFAULT);
            bot.execute(messageUtils.prepareSendMessage(chatId, "new.link.command.short.url"));
            return;
        }

        Customer customer = urlShortenerService.getOrCreateCustomerByChatId(chatId);
        Link newLink = urlShortenerService.saveNewLink(customer, url);

        SendMessage sMessage = new SendMessage(chatId.toString(),
                messageUtils.getI18nMessageFor("new.link.command.response")
                        + urlBuilder.buildUrlWithDomain(newLink.getToken()));

        bot.execute(sMessage);

        metaData.setCommandType(DEFAULT);
    }
}