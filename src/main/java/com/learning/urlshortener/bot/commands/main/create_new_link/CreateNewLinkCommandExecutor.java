package com.learning.urlshortener.bot.commands.main.create_new_link;

import static com.learning.urlshortener.bot.commands.CommandType.DEFAULT;
import static com.learning.urlshortener.bot.commands.CommandType.NEW_LINK;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import com.learning.urlshortener.bot.commands.CommandType;
import com.learning.urlshortener.bot.commands.main.AbstractCommandExecutor;
import com.learning.urlshortener.bot.commands.main.state.ChatMetaData;
import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;
import com.learning.urlshortener.services.urlvalidation.validators.UrlSyntaxValidationException;
import com.learning.urlshortener.services.urlvalidation.validators.UrlLengthValidationException;
import com.learning.urlshortener.services.urlvalidation.validators.UrlValidationException;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Component
@RequiredArgsConstructor
public class CreateNewLinkCommandExecutor extends AbstractCommandExecutor {

    @Override
    public CommandType getExecutorCommand() {
        return NEW_LINK;
    }

    @Override
    public void execute(ChatMetaData metaData) {
        String url = metaData.getMessage();
        Long chatId = metaData.getChatId();
        Customer customer = urlShortenerService.getOrCreateCustomerByChatId(chatId);

        try {
            Link newLink = urlShortenerService.saveNewLink(customer, url);
            metaData.setCommandType(DEFAULT);
            executeBotResponseForNewLink(chatId, newLink);
        } catch (UrlValidationException urlValidationException) {
            processNonValidUrl(urlValidationException, metaData);
        }
    }

    @SneakyThrows
    private void executeBotResponseForNewLink(Long chatId, Link newLink) {

        SendMessage sMessage = new SendMessage(chatId.toString(),
                messageUtils.getI18nMessageFor("new.link.command.response")
                        + urlBuilder.buildUrlWithDomain(newLink.getToken()));

        bot.execute(sMessage);
    }

    @SneakyThrows
    private void processNonValidUrl(UrlValidationException exception, ChatMetaData metaData) {

        if (exception instanceof UrlLengthValidationException) {
            metaData.setCommandType(DEFAULT);
        }

        String template = Map.of(
                exception instanceof UrlSyntaxValidationException, "new.link.command.invalid.url",
                exception instanceof UrlLengthValidationException, "new.link.command.short.url").get(true);

        bot.execute(messageUtils.prepareSendMessage(metaData.getChatId(), template));
    }
}