package com.learning.urlshortener.bot.commands.main;

import com.learning.urlshortener.bot.commands.CommandType;
import com.learning.urlshortener.bot.commands.main.state.ChatMetaData;

public interface CommandExecutor {

    CommandType getExecutorCommand();

    void execute(ChatMetaData metaData);
}