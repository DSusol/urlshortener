package com.learning.urlshortener.bot.commands.main.create_new_link;

import static com.learning.urlshortener.bot.commands.CommandType.NEW_LINK;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import com.learning.urlshortener.bot.commands.AbstractCommand;

import lombok.SneakyThrows;

@Order(1)
@Qualifier("MainMenuCommands")
@Component
class CreateNewLinkCommand extends AbstractCommand {

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
        absSender.execute(messageHandler.prepareSendMessage(message, "new.link.command.request.url"));
    }
}