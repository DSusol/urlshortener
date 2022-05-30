package com.learning.urlshortener.bot.commands.main;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.learning.urlshortener.bot.commands.main.state.ChatMetaData;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CommandExecution {

    private final Set<CommandExecutor> executors;

    public void execute(ChatMetaData metaData) {
        for(CommandExecutor executor: executors) {
            if(executor.getExecutorCommand() == metaData.getCommand()) {
                executor.execute(metaData);
                return;
            }
        }
    }
}
