package com.learning.urlshortener.bot.commands.main.create_new_link.executors.utils;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import com.learning.urlshortener.bot.commands.main.state.ChatMetaData;
import com.learning.urlshortener.bot.utils.MessageUtils;
import com.learning.urlshortener.bot.utils.UrlBuilder;
import com.learning.urlshortener.domain.Link;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NewLinkUtils {

    private final MessageUtils messageUtils;
    private final UrlBuilder urlBuilder;

    public SendMessage prepareNewLinkSendMessage(ChatMetaData metaData, Link newLink) {
        return new SendMessage(metaData.getChatId().toString(),
                messageUtils.getI18nMessageFor("new.link.command.response")
                        + urlBuilder.buildUrlWithDomain(newLink.getToken()));
    }
}