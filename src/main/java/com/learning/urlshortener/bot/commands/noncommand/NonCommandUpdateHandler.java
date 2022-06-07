package com.learning.urlshortener.bot.commands.noncommand;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import com.learning.urlshortener.bot.commands.main.state.MultiStepCommandHandler;
import com.learning.urlshortener.bot.utils.MessageUtils;

import lombok.SneakyThrows;

@Component
public class NonCommandUpdateHandler {

    private final AbsSender bot;
    private final MultiStepCommandHandler commandHandler;
    private final MessageUtils messageUtils;

    public NonCommandUpdateHandler(@Lazy AbsSender bot, MultiStepCommandHandler commandHandler,
                                   MessageUtils messageUtils) {
        this.bot = bot;
        this.commandHandler = commandHandler;
        this.messageUtils = messageUtils;
    }

    @SneakyThrows
    public void handleUpdate(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        if (commandHandler.updateApplicableForMultiStepProcessing(update)) {
            commandHandler.processNextStep(update);
            return;
        }

        Message message = update.getMessage();
        SendMessage sendMessage = messageUtils.prepareSendMessage(message, "bot.default.message");
        bot.execute(sendMessage);
    }
}