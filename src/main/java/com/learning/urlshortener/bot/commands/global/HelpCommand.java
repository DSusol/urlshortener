package com.learning.urlshortener.bot.commands.global;

import static com.learning.urlshortener.bot.commands.CommandType.HELP;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import com.learning.urlshortener.bot.commands.AbstractCommand;
import com.learning.urlshortener.bot.commands.main.MainMenuCommands;

import lombok.SneakyThrows;

@Component
public class HelpCommand extends AbstractCommand {

    @Autowired
    @MainMenuCommands
    private List<IBotCommand> sortedBotCommands;

    @Override
    public String getCommandIdentifier() {
        return HELP.getCommandIdentifier();
    }

    @Override
    public String getDescription() {
        return null;
    }

    @SneakyThrows
    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        StringBuilder helpMessageBuilder = new StringBuilder();

        helpMessageBuilder.append(messageHandler.getI18nMessageFor("bot.help.header"));
        sortedBotCommands.forEach(command -> helpMessageBuilder.append("/")
                .append(command.getCommandIdentifier()).append(" - ")
                .append(messageHandler.getI18nMessageFor(command.getDescription())).append("\n"));

        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), helpMessageBuilder.toString());
        sendMessage.enableHtml(true);
        absSender.execute(sendMessage);
    }
}