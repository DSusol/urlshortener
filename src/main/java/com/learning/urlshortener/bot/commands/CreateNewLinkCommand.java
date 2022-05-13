package com.learning.urlshortener.bot.commands;

import static com.learning.urlshortener.bot.commands.CommandNamesStorage.CREATE_NEW_LINK_DESCRIPTION;
import static com.learning.urlshortener.bot.commands.CommandNamesStorage.CREATE_NEW_LINK_IDENTIFIER;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import com.learning.urlshortener.service.BotService;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
@Component
public class CreateNewLinkCommand implements IBotCommand {

    private final BotService botService;

    @Override
    public String getCommandIdentifier() {
        return CREATE_NEW_LINK_IDENTIFIER;
    }

    @Override
    public String getDescription() {
        return CREATE_NEW_LINK_DESCRIPTION;
    }

    @SneakyThrows
    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        //temporary implementation:
        SendMessage sendMessage = SendMessage.builder()
                .chatId(message.getChatId().toString())
                .text("will create new link")
                .build();
        absSender.execute(sendMessage);
    }
}