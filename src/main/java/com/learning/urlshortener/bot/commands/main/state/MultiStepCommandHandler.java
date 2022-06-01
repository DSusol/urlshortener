package com.learning.urlshortener.bot.commands.main.state;

import static com.learning.urlshortener.bot.commands.CommandType.DEFAULT;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.learning.urlshortener.bot.commands.CommandType;
import com.learning.urlshortener.bot.commands.main.CommandExecutor;

@Component
public class MultiStepCommandHandler {

    private final Function<Long, ChatMetaData> metaDataProvider;
    private final Map<CommandType, CommandExecutor> executors = new HashMap<>();
    private final Map<Long, ChatMetaData> chatStates = new HashMap<>();

    @Autowired
    public MultiStepCommandHandler(Function<Long, ChatMetaData> metaDataProvider, Set<CommandExecutor> executors) {
        this.metaDataProvider = metaDataProvider;
        executors.forEach(executor -> this.executors.put(executor.getExecutorCommand(), executor));
    }

    public void setChatExecutingCommand(Long chatId, CommandType commandType) {
        ChatMetaData metaData = chatStates.get(chatId);
        if (metaData == null) {
            metaData = metaDataProvider.apply(chatId);
        }

        metaData.setCommandType(commandType);
        chatStates.put(chatId, metaData);
    }

    public boolean updateApplicableForMultiStepProcessing(Update update) {
        ChatMetaData metaData = getChatMetaData(update);
        return metaData != null && metaData.getCommandType() != DEFAULT;
    }

    public void processNextStep(Update update) {
        ChatMetaData metaData = getChatMetaData(update);
        metaData.setMessage(update.getMessage().getText());

        executors.get(metaData.getCommandType()).execute(metaData);
    }

    private ChatMetaData getChatMetaData(Update update) {
        return chatStates.get(update.getMessage().getChatId());
    }
}