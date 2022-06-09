package com.learning.urlshortener.bot.commands.main.state;

import java.util.ArrayList;
import java.util.List;

import com.learning.urlshortener.bot.commands.CommandType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMetaData {

    // Received from bot
    private Long chatId;
    private CommandType commandType;
    private String message;

    // Command state
    private CommandState commandState;
    private List<String> args = new ArrayList<>();

    public ChatMetaData(Long chatId) {
        this.chatId = chatId;
    }
}