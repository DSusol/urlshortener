package com.learning.urlshortener.bot.commands.main.create_new_link;

import static com.learning.urlshortener.bot.commands.CommandType.NEW_LINK;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import com.learning.urlshortener.bot.commands.AbstractIBotCommand;
import com.learning.urlshortener.bot.commands.main.MainMenuCommands;

import lombok.SneakyThrows;

@Order(1)
@MainMenuCommands
@Component
class CreateNewLinkIBotCommand extends AbstractIBotCommand {

    @Override
    public String getCommandIdentifier() {
        return NEW_LINK.getCommandIdentifier();
    }

    @Override
    public String getDescription() {
        return "new.link.command.description";
    }

    @SneakyThrows
    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        multiStepCommandHandler.setChatExecutingCommand(message.getChatId(), NEW_LINK);
        absSender.execute(messageUtils.prepareSendMessage(message, "new.link.command.request.url"));
    }
}