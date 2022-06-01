package com.learning.urlshortener.bot.commands.main.state;

import com.learning.urlshortener.bot.commands.CommandType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMetaData {

    private Long chatId;
    private CommandType commandType;
    private String message;

    public ChatMetaData(Long chatId) {
        this.chatId = chatId;
    }
}