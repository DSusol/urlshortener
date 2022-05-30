package com.learning.urlshortener.bot.commands.main;

import com.learning.urlshortener.bot.commands.Command;
import com.learning.urlshortener.bot.commands.main.state.ChatMetaData;

public interface CommandExecutor {

    Command getExecutorCommand();

    void execute(ChatMetaData metaData);
}
