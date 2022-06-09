package com.learning.urlshortener.bot.commands.main.create_new_link.executors;

import static com.learning.urlshortener.bot.commands.main.create_new_link.executors.CreateNewLinkCommandState.DUPLICATE_URL_QUESTION;
import static com.learning.urlshortener.bot.commands.main.create_new_link.executors.CreateNewLinkCommandState.NEW_LINK_START;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.learning.urlshortener.bot.commands.main.create_new_link.executors.utils.NewLinkUtils;
import com.learning.urlshortener.bot.commands.main.state.AbstractCommandStateExecutor;
import com.learning.urlshortener.bot.commands.main.state.ChatMetaData;
import com.learning.urlshortener.bot.commands.main.state.CommandState;
import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;
import com.learning.urlshortener.services.urlvalidation.UrlValidationException;
import com.learning.urlshortener.services.urlvalidation.exceptions.ExistingUrlException;
import com.learning.urlshortener.services.urlvalidation.exceptions.UrlLengthValidationException;
import com.learning.urlshortener.services.urlvalidation.exceptions.UrlSyntaxValidationException;

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
        } catch (UrlSyntaxValidationException urlSyntaxValidationException) {
            senMessageForInvalidUrl(metaData, urlSyntaxValidationException);
            return false;
        } catch (UrlLengthValidationException urlLengthValidationException) {
            senMessageForInvalidUrl(metaData, urlLengthValidationException);
            return true;
        } catch (ExistingUrlException existingUrlException) {
            processExistingUlr(metaData);
            return false;
        }
    }

    @SneakyThrows
    private void sendMessageForNewUrl(ChatMetaData metaData, Link newLink) {
        bot.execute(newLinkUtils.prepareNewLinkSendMessage(metaData, newLink));
    }

    @SneakyThrows
    private void senMessageForInvalidUrl(ChatMetaData metaData, UrlValidationException exception) {

        String template = Map.of(
                exception instanceof UrlSyntaxValidationException, "new.link.command.invalid.url",
                exception instanceof UrlLengthValidationException, "new.link.command.short.url").get(true);

        bot.execute(messageUtils.prepareSendMessage(metaData.getChatId(), template));
    }

    @SneakyThrows
    private void processExistingUlr(ChatMetaData metaData) {
        bot.execute(messageUtils.prepareSendMessage(metaData.getChatId(), "new.link.command.duplicate.url"));
        metaData.setCommandState(DUPLICATE_URL_QUESTION);
        metaData.getArgs().add(metaData.getMessage());
    }
}