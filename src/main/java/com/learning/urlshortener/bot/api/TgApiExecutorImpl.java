package com.learning.urlshortener.bot.api;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;

import lombok.SneakyThrows;

@Component
@Profile("!test")
class TgApiExecutorImpl implements TgApiExecutor {

    private final AbsSender bot;

    TgApiExecutorImpl(@Lazy AbsSender bot) {
        this.bot = bot;
    }

    @SneakyThrows
    @Override
    public void executeSendMessage(SendMessage sendMessage) {
        bot.execute(sendMessage);
    }
}
