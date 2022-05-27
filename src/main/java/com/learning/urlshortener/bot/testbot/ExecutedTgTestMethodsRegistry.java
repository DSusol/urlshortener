package com.learning.urlshortener.bot.testbot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import lombok.Getter;

@Component
@Getter
public class ExecutedTgTestMethodsRegistry {

    private final Map<String, List<SendMessage>> executedSendMessages = new HashMap<>();
    private final List<BotApiMethod<?>> executedMethods = new ArrayList<>();

    public void handleMethod(BotApiMethod<?> method) {
        if (!(method instanceof SendMessage)) {
            executedMethods.add(method);
            return;
        }

        SendMessage sendMessage = (SendMessage) method;
        String chatId = sendMessage.getChatId();

        if (executedSendMessages.containsKey(chatId)) {
            getAllSendMessagesForChatId(Long.valueOf(chatId)).add(sendMessage);
            return;
        }

        executedSendMessages.put(chatId, new ArrayList<>(List.of(sendMessage)));
    }

    public String getLastSendMessageTextForChatId(Long chatId) {
        List<SendMessage> sendMessages = getAllSendMessagesForChatId(chatId);
        return sendMessages.get(sendMessages.size() - 1).getText();
    }

    public List<SendMessage> getAllSendMessagesForChatId(Long chatId) {
        return executedSendMessages.get(chatId.toString());
    }

    public void clearAllSendMessages() {
        executedSendMessages.clear();
    }
}