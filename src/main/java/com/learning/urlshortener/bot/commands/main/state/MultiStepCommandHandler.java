package com.learning.urlshortener.bot.commands.main.state;

import static com.learning.urlshortener.bot.commands.Command.DEFAULT;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.learning.urlshortener.bot.commands.Command;
import com.learning.urlshortener.bot.commands.main.CommandExecution;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class MultiStepCommandHandler {

    private final ApplicationContext applicationContext;
    private final CommandExecution commandExecution;
    private final Map<Long, ChatMetaData> chatStates = new HashMap<>();

    public void setChatExecutingCommand(Long chatId, Command command) {
        ChatMetaData metaData = chatStates.get(chatId);
        if (metaData != null) {
            metaData.setCommand(command);
            chatStates.put(chatId, metaData);
            return;
        }

        metaData = applicationContext.getBean(ChatMetaData.class, chatId);
        metaData.setCommand(command);
        chatStates.put(chatId, metaData);
    }

    public boolean shouldSendMessageToCommand(Message message) {
        ChatMetaData metaData = chatStates.get(message.getChatId());
        return metaData != null && metaData.getCommand() != DEFAULT;
    }

    public void executeCommand(Message message) {
        ChatMetaData metaData = chatStates.get(message.getChatId());
        metaData.setMessage(message.getText());
        commandExecution.execute(metaData);
    }
}