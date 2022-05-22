package com.learning.urlshortener.bot.commands;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.learning.urlshortener.bot.UrlShortenerBot;
import com.learning.urlshortener.bot.api.TgApiExecutor;
import com.learning.urlshortener.bot.utils.MessageHandler;

@Component
public class HelpHandler {

    private final UrlShortenerBot urlShortenerBot;
    private final TgApiExecutor apiExecutor;
    private final MessageHandler messageHandler;

    public HelpHandler(@Lazy UrlShortenerBot urlShortenerBot, TgApiExecutor apiExecutor, MessageHandler messageHandler) {
        this.urlShortenerBot = urlShortenerBot;
        this.apiExecutor = apiExecutor;
        this.messageHandler = messageHandler;
    }

    public void getHelpMessage(Message message) {

        String languageCode = message.getFrom().getLanguageCode();
        StringBuilder helpMessageBuilder = new StringBuilder();

        helpMessageBuilder.append(messageHandler.getI18nMessageFor("bot.help.header", languageCode));
        urlShortenerBot.getSortedBotCommands().forEach(command -> helpMessageBuilder.append("/")
                .append(command.getCommandIdentifier()).append(" - ")
                .append(messageHandler.getI18nMessageFor(command.getDescription(), languageCode)).append("\n"));

        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), helpMessageBuilder.toString());
        sendMessage.enableHtml(true);
        apiExecutor.executeSendMessage(sendMessage);
    }
}