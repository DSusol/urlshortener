package com.learning.urlshortener.bot.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
@Profile("test")
public class TgApiExecutorTestImpl implements TgApiExecutor {

    Map<String, SendMessage> sendMessages = new HashMap<>();

    @Override
    public void executeSendMessage(AbsSender bot, SendMessage sendMessage) {
        sendMessages.put(sendMessage.getChatId(), sendMessage);
    }
}