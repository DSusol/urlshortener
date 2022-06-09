package com.learning.urlshortener.bot.commands.main.create_new_link;

import com.learning.urlshortener.bot.commands.main.state.CommandState;

public enum CreateNewLinkCommandState implements CommandState {
    START,
    DUPLICATE_URL_QUESTION
}