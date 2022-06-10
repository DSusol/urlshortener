package com.learning.urlshortener.bot.commands.main.create_new_link.executors;

import static com.learning.urlshortener.bot.commands.main.create_new_link.executors.CreateNewLinkCommandState.DUPLICATE_URL_QUESTION;
import static com.learning.urlshortener.bot.commands.main.create_new_link.executors.CreateNewLinkCommandState.NEW_LINK_START;
import static com.learning.urlshortener.services.urlvalidation.UrlValidationExceptionCause.EXISTING_URL;
import static com.learning.urlshortener.services.urlvalidation.UrlValidationExceptionCause.INVALID_SYNTAX;
import static com.learning.urlshortener.services.urlvalidation.UrlValidationExceptionCause.SHORT_LENGTH;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.learning.urlshortener.bot.commands.main.create_new_link.executors.utils.NewLinkUtils;
import com.learning.urlshortener.bot.commands.main.state.AbstractCommandStateExecutor;
import com.learning.urlshortener.bot.commands.main.state.ChatMetaData;
import com.learning.urlshortener.bot.commands.main.state.CommandState;
import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;
import com.learning.urlshortener.services.urlvalidation.UrlValidationExceptionCause;
import com.learning.urlshortener.services.urlvalidation.UrlValidationException;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Component
@CreateNewLinkExecutors
@RequiredArgsConstructor
public class StartStateExecutor extends AbstractCommandStateExecutor {

    private final NewLinkUtils newLinkUtils;

    @Override
    public CommandState getCommandState() {
        return NEW_LINK_START;
    }

    @SneakyThrows
    @Override
    public boolean executeStateAndReturnCommandFinishStatus(ChatMetaData metaData) {

        Customer customer = urlShortenerService.getOrCreateCustomerByChatId(metaData.getChatId());

        try {
            Link newLink = urlShortenerService.saveNewLink(customer, metaData.getMessage());
            sendMessageForNewUrl(metaData, newLink);
            return true;
        } catch (UrlValidationException urlValidationException) {
            UrlValidationExceptionCause cause = urlValidationException.getUrlValidationExceptionCause();

            if(cause == SHORT_LENGTH) {
                senMessageForInvalidUrl(metaData, SHORT_LENGTH);
                return true;
            }

            if(cause == INVALID_SYNTAX) {
                senMessageForInvalidUrl(metaData, INVALID_SYNTAX);
            }

            if(cause == EXISTING_URL) {
                processExistingUlr(metaData);
            }

            return false;
        }
    }

    @SneakyThrows
    private void sendMessageForNewUrl(ChatMetaData metaData, Link newLink) {
        bot.execute(newLinkUtils.prepareNewLinkSendMessage(metaData, newLink));
    }

    @SneakyThrows
    private void senMessageForInvalidUrl(ChatMetaData metaData, UrlValidationExceptionCause cause) {

        String template = Map.of(
                INVALID_SYNTAX, "new.link.command.invalid.url",
                SHORT_LENGTH, "new.link.command.short.url").get(cause);

        bot.execute(messageUtils.prepareSendMessage(metaData.getChatId(), template));
    }

    @SneakyThrows
    private void processExistingUlr(ChatMetaData metaData) {
        bot.execute(messageUtils.prepareSendMessage(metaData.getChatId(), "new.link.command.duplicate.url"));
        metaData.setCommandState(DUPLICATE_URL_QUESTION);
        metaData.getArgs().add(metaData.getMessage());
    }
}