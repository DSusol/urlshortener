package com.learning.urlshortener.bot.commands.global;

import static com.learning.urlshortener.bot.commands.CommandType.START;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import com.learning.urlshortener.bot.commands.AbstractIBotCommand;

import lombok.SneakyThrows;

@Component
public class StartIBotCommand extends AbstractIBotCommand {

    @Override
    public String getCommandIdentifier() {
        return START.getCommandIdentifier();
    }

    @Override
    public String getDescription() {
        return null;
    }

    @SneakyThrows
    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        SendMessage sendMessage = messageUtils.prepareSendMessage(message, "bot.welcome.message");
        absSender.execute(sendMessage);
    }
}
