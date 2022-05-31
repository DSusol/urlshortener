package com.learning.urlshortener.bot.commands.main;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.learning.urlshortener.bot.commands.main.state.ChatMetaData;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@Component
@AllArgsConstructor
public class CommandExecution {

    private final Set<CommandExecutor> executors;

    @SneakyThrows
    public void execute(ChatMetaData metaData) {
        executors.stream()
                .filter(executor -> executor.getExecutorCommand() == metaData.getCommand())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No command executor available for " + metaData.getCommand()))
                .execute(metaData);
    }
}