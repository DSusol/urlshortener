package com.learning.urlshortener.bot.state;

import static com.learning.urlshortener.bot.commands.AbstractCommand.Command.DEFAULT;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.EntityType;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.learning.urlshortener.bot.commands.AbstractCommand.Command;

@Component
public class StateHandler {

    private final Map<Long, Command> chatStates = new HashMap<>();

    public void setChatState(Long chatId, Command command) {
        chatStates.put(chatId, command);
    }

    public Update handleUpdate(Update update) {
        if (!update.hasMessage()) {
            return update;
        }

        Message message = update.getMessage();
        Command command = chatStates.get(message.getChatId());

        if (command == null || command == DEFAULT) {
            return update;
        }

        Message commandMessage = convertMessageToCommandMessage(message, command);
        update.setMessage(commandMessage);

        return update;
    }

    private Message convertMessageToCommandMessage(Message message, Command command) {

        MessageEntity commandEntity = new MessageEntity(EntityType.BOTCOMMAND, 0, command.name().length());

        message.setText("/" + command.name().toLowerCase() + " " + message.getText());
        message.setEntities(List.of(commandEntity));

        return message;
    }
}
