package com.learning.urlshortener.bot.commands.main.create_new_link.executors;

import com.learning.urlshortener.bot.commands.main.state.CommandState;

public enum CreateNewLinkCommandState implements CommandState {
    NEW_LINK_START,
    DUPLICATE_URL_QUESTION
}