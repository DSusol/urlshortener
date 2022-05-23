package com.learning.urlshortener.bot.commands;

import java.util.List;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.learning.urlshortener.bot.utils.MessageHandler;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@Component
@AllArgsConstructor
public class HelpHandler {

    private final List<IBotCommand> sortedBotCommands;
    private final MessageHandler messageHandler;

    @SneakyThrows
    public SendMessage getHelpMessage(Message message) {

        StringBuilder helpMessageBuilder = new StringBuilder();

        helpMessageBuilder.append(messageHandler.getI18nMessageFor("bot.help.header"));
        sortedBotCommands.forEach(command -> helpMessageBuilder.append("/")
                .append(command.getCommandIdentifier()).append(" - ")
                .append(messageHandler.getI18nMessageFor(command.getDescription())).append("\n"));

        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), helpMessageBuilder.toString());
        sendMessage.enableHtml(true);
        return sendMessage;
    }
}