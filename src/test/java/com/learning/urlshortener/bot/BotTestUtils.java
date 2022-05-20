package com.learning.urlshortener.bot;

import java.util.List;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.EntityType;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

public class BotTestUtils {

    public static Update createUpdateWithMessageFromChat(Long chatId, String messageText, String languageCode) {
        User testUser = new User();
        testUser.setLanguageCode(languageCode);

        Chat testChat = new Chat();
        testChat.setId(chatId);

        Message testMessage = new Message();
        testMessage.setChat(testChat);
        testMessage.setFrom(testUser);
        testMessage.setText(messageText);

        Update testUpdate = new Update();
        testUpdate.setMessage(testMessage);

        return testUpdate;
    }

    public static Update createCommandUpdateWithMessageFromChat(Long chatId, String messageText, String languageCode) {
        MessageEntity testMessageEntity = new MessageEntity();
        testMessageEntity.setType(EntityType.BOTCOMMAND);
        testMessageEntity.setOffset(0);
        testMessageEntity.setLength(1);

        Update testUpdate = createUpdateWithMessageFromChat(chatId, messageText, languageCode);
        testUpdate.getMessage().setEntities(List.of(testMessageEntity));

        return testUpdate;
    }
}
