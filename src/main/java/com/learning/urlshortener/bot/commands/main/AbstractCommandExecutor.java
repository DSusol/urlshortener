package com.learning.urlshortener.bot.commands.main;

import static com.learning.urlshortener.bot.commands.CommandType.DEFAULT;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.learning.urlshortener.bot.commands.main.state.AbstractCommandStateExecutor;
import com.learning.urlshortener.bot.commands.main.state.ChatMetaData;
import com.learning.urlshortener.bot.commands.main.state.CommandState;

public abstract class AbstractCommandExecutor implements CommandExecutor {

    private final CommandState startingCommandState;
    private final Map<CommandState, AbstractCommandStateExecutor> executors = new HashMap<>();

    public AbstractCommandExecutor(Set<AbstractCommandStateExecutor> executors, CommandState startingCommandState) {
        executors.forEach(executor -> this.executors.put(executor.getCommandState(), executor));
        this.startingCommandState = startingCommandState;
    }

    @Override
    public void execute(ChatMetaData metaData) {

        CommandState currentCommandState = metaData.getCommandState();

        if (currentCommandState == null) {
            currentCommandState = startingCommandState;
        }

        boolean isFinished = executors.get(currentCommandState).executeStateAndReturnCommandFinishStatus(metaData);

        if (isFinished) {
            metaData.setCommandState(startingCommandState);
            metaData.getArgs().clear();
            metaData.setCommandType(DEFAULT);
        }
    }
}
