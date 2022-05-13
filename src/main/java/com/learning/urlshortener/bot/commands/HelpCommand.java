package com.learning.urlshortener.bot.commands;

import static com.learning.urlshortener.bot.commands.CommandNamesStorage.HELP_DESCRIPTION;
import static com.learning.urlshortener.bot.commands.CommandNamesStorage.HELP_IDENTIFIER;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.ICommandRegistry;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import lombok.SneakyThrows;

public class HelpCommand implements IBotCommand {

    private final ICommandRegistry mCommandRegistry;

    public HelpCommand(ICommandRegistry mCommandRegistry) {
        this.mCommandRegistry = mCommandRegistry;
    }

    @Override
    public String getCommandIdentifier() {
        return HELP_IDENTIFIER;
    }

    @Override
    public String getDescription() {
        return HELP_DESCRIPTION;
    }

    @SneakyThrows
    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        StringBuilder helpMessageBuilder = new StringBuilder("<b>Available commands:\n</b>");

        mCommandRegistry.getRegisteredCommands().forEach(cmd ->
                helpMessageBuilder.append("/").append(cmd.getCommandIdentifier())
                        .append(" - ").append(cmd.getDescription()).append("\n"));

        SendMessage sendMessage = SendMessage.builder()
                .chatId(message.getChatId().toString())
                .text(helpMessageBuilder.toString())
                .build();
        sendMessage.enableHtml(true);

        absSender.execute(sendMessage);
    }
}