package com.learning.urlshortener.bot.api;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;

import lombok.SneakyThrows;

@Component
@Profile("!test")
class TgApiExecutorImpl implements TgApiExecutor {

    @SneakyThrows
    @Override
    public void executeSendMessage(AbsSender bot, SendMessage sendMessage) {
        bot.execute(sendMessage);
    }
}
