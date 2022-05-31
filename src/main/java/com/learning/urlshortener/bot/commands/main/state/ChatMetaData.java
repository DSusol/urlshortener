package com.learning.urlshortener.bot.commands.main.state;

import com.learning.urlshortener.bot.commands.Command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ChatMetaData {

    private final Long chatId;
    private Command command;
    private String message;
}