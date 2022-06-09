package com.learning.urlshortener.bot.commands.main.create_new_link;

import static com.learning.urlshortener.bot.commands.CommandType.NEW_LINK;
import static com.learning.urlshortener.bot.commands.main.create_new_link.executors.CreateNewLinkCommandState.NEW_LINK_START;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.learning.urlshortener.bot.commands.CommandType;
import com.learning.urlshortener.bot.commands.main.AbstractCommandExecutor;
import com.learning.urlshortener.bot.commands.main.create_new_link.executors.CreateNewLinkExecutors;
import com.learning.urlshortener.bot.commands.main.state.AbstractCommandStateExecutor;

/**
 * <b>NEW LINK COMMAND FLOW:</b><br><br>
 * <font color="green">START</font> [ ]<br>
 * &emsp|<br>
 * &emsp|_____ create new link with valid distinct URL and finish;<br>
 * &emsp|_____ URL is too short -----> abort link creation and finish;<br>
 * &emsp|_____ URL is invalid -----> ask for the new URL -----> <font color="green">START</font> [ ];<br>
 * &emsp|<br>
 * &emsp URL is duplicated<br>
 * &emsp|<br>
 * &emsp|_____<font color="green">DUPLICATE_URL_QUESTION</font> [ <font color="orange">url</font> ]<br>
 * &emsp&emsp&emsp&emsp&emsp|<br>
 * &emsp&emsp&emsp&emsp&emsp|_____ if answer is yes -----> create new link with duplicated url and finish;<br>
 * &emsp&emsp&emsp&emsp&emsp|_____ if answer is no -----> abort link creation and finish;<br>
 * <br><br>
 * Color Legend:<br>
 * <font color="green">MetaData.commandState</font><br>
 * <font color="orange">MetaData.args</font><br>
 */
@Component
public class CreateNewLinkCommandExecutor extends AbstractCommandExecutor {

    @Autowired
    public CreateNewLinkCommandExecutor(@CreateNewLinkExecutors Set<AbstractCommandStateExecutor> executors) {
        super(executors, NEW_LINK_START);
    }

    @Override
    public CommandType getExecutorCommand() {
        return NEW_LINK;
    }
}