package com.learning.urlshortener.bot.commands.main.create_new_link;

import static com.learning.urlshortener.bot.commands.CommandType.DEFAULT;
import static com.learning.urlshortener.bot.commands.CommandType.NEW_LINK;
import static com.learning.urlshortener.bot.commands.main.create_new_link.CreateNewLinkCommandState.DUPLICATE_URL_QUESTION;
import static com.learning.urlshortener.bot.commands.main.create_new_link.CreateNewLinkCommandState.START;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import com.learning.urlshortener.bot.commands.CommandType;
import com.learning.urlshortener.bot.commands.main.AbstractCommandExecutor;
import com.learning.urlshortener.bot.commands.main.state.ChatMetaData;
import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;
import com.learning.urlshortener.services.urlvalidation.UrlValidationException;
import com.learning.urlshortener.services.urlvalidation.exceptions.ExistingUrlException;
import com.learning.urlshortener.services.urlvalidation.exceptions.UrlLengthValidationException;
import com.learning.urlshortener.services.urlvalidation.exceptions.UrlSyntaxValidationException;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

/**
 * <b>NEW LINK COMMAND FLOW:</b><br><br>
 * <font color="green">START</font> [ ]<br>
 * &emsp|<br>
 * &emsp|_____ create new link with valid distinct URL and finish;<br>
 * &emsp|_____ URL is too short -----> abort link creation and finish;<br>
 * &emsp|_____ URL is invalid -----> ask for the new URL -----> <font color="green">START</font> [ ];<br>
 * &emsp|<br>
 * &emsp URL is duplicated<br>
 * &emsp|<br>
 * &emsp|_____<font color="green">DUPLICATE_URL_QUESTION</font> [ <font color="orange">url</font> ]<br>
 * &emsp&emsp&emsp&emsp&emsp|<br>
 * &emsp&emsp&emsp&emsp&emsp|_____ if answer is yes -----> create new link with duplicated url and finish;<br>
 * &emsp&emsp&emsp&emsp&emsp|_____ if answer is no -----> abort link creation and finish;<br>
 * <br><br>
 * Color Legend:<br>
 * <font color="green">MetaData.commandState</font><br>
 * <font color="orange">MetaData.args</font><br>
 */
@Component
@RequiredArgsConstructor
public class CreateNewLinkCommandExecutor extends AbstractCommandExecutor {

    @Override
    public CommandType getExecutorCommand() {
        return NEW_LINK;
    }

    @Override
    public void execute(ChatMetaData metaData) {

        boolean isFinished = false;

        if(metaData.getCommandState() == null || metaData.getCommandState() == START) {
            isFinished = startProcessingUrl(metaData, metaData.getMessage(), false);
        } else if(metaData.getCommandState() == DUPLICATE_URL_QUESTION){
            isFinished = processDuplicateRespond(metaData);
        }

        if (isFinished) {
            metaData.setCommandState(START);
            metaData.getArgs().clear();
            metaData.setCommandType(DEFAULT);
        }
    }

    private boolean startProcessingUrl(ChatMetaData metaData, String urlName, boolean allowedToMakeDuplicateUrl) {

        Customer customer = urlShortenerService.getOrCreateCustomerByChatId(metaData.getChatId());

        try {
            Link newLink = allowedToMakeDuplicateUrl
                    ? urlShortenerService.saveNewLink(customer, urlName, true)
                    : urlShortenerService.saveNewLink(customer, urlName);
            return processNewUrl(metaData, newLink);
        } catch (ExistingUrlException existingUrlException) {
            return processExistingUlr(metaData);
        } catch (UrlValidationException urlValidationException) {
            return processInvalidUrl(metaData, urlValidationException);
        }
    }

    @SneakyThrows
    private boolean processNewUrl(ChatMetaData metaData, Link newLink) {

        SendMessage sMessage = new SendMessage(metaData.getChatId().toString(),
                messageUtils.getI18nMessageFor("new.link.command.response")
                        + urlBuilder.buildUrlWithDomain(newLink.getToken()));

        bot.execute(sMessage);
        metaData.getArgs().clear();
        return true;
    }

    @SneakyThrows
    private boolean processExistingUlr(ChatMetaData metaData) {
        bot.execute(messageUtils.prepareSendMessage(metaData.getChatId(), "new.link.command.override.url"));
        metaData.setCommandState(DUPLICATE_URL_QUESTION);
        metaData.getArgs().add(metaData.getMessage());
        return false;
    }

    @SneakyThrows
    private boolean processInvalidUrl(ChatMetaData metaData, UrlValidationException exception) {

        String template = Map.of(
                exception instanceof UrlSyntaxValidationException, "new.link.command.invalid.url",
                exception instanceof UrlLengthValidationException, "new.link.command.short.url").get(true);

        bot.execute(messageUtils.prepareSendMessage(metaData.getChatId(), template));

        return exception instanceof UrlLengthValidationException;
    }

    @SneakyThrows
    private boolean processDuplicateRespond(ChatMetaData metaData) {
        String response = metaData.getMessage().toLowerCase();
        String yesAnswer = messageUtils.getI18nMessageFor("yes.answer");
        String noAnswer = messageUtils.getI18nMessageFor("no.answer");

        if (!response.equals(yesAnswer) && !response.equals(noAnswer)) {
            bot.execute(messageUtils.prepareSendMessage(metaData.getChatId(), "unrecognized.answer"));
            return false;
        }

        if (response.equals(noAnswer)) {
            metaData.getArgs().clear();
            return true;
        }

        return startProcessingUrl(metaData, metaData.getArgs().get(0), true);
    }
}