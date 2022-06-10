package com.learning.urlshortener.bot.commands.main.create_new_link.executors;

import static com.learning.urlshortener.bot.commands.main.create_new_link.executors.CreateNewLinkCommandState.DUPLICATE_URL_QUESTION;

import org.springframework.stereotype.Component;

import com.learning.urlshortener.bot.commands.main.create_new_link.executors.utils.NewLinkUtils;
import com.learning.urlshortener.bot.commands.main.state.AbstractCommandStateExecutor;
import com.learning.urlshortener.bot.commands.main.state.ChatMetaData;
import com.learning.urlshortener.bot.commands.main.state.CommandState;
import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Component
@CreateNewLinkExecutors
@RequiredArgsConstructor
public class DuplicateUrlStateExecutor extends AbstractCommandStateExecutor {

    private final NewLinkUtils newLinkUtils;

    @Override
    public CommandState getCommandState() {
        return DUPLICATE_URL_QUESTION;
    }

    @SneakyThrows
    @Override
    public boolean executeStateAndReturnCommandFinishStatus(ChatMetaData metaData) {
        String response = metaData.getMessage().toLowerCase();
        String yesAnswer = messageUtils.getI18nMessageFor("yes.answer");
        String noAnswer = messageUtils.getI18nMessageFor("no.answer");

        if (response.equals(noAnswer)) {
            return true;
        }

        if (!response.equals(yesAnswer)) {
            bot.execute(messageUtils.prepareSendMessage(metaData.getChatId(), "unrecognized.answer"));
            return false;
        }

        Customer customer = urlShortenerService.getOrCreateCustomerByChatId(metaData.getChatId());
        Link newLink = urlShortenerService.saveValidatedNewLink(customer, metaData.getArgs().get(0));
        bot.execute(newLinkUtils.prepareNewLinkSendMessage(metaData, newLink));

        return true;
    }
}

