package com.learning.urlshortener.bot.commands.global;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.learning.urlshortener.BaseFullContextTest;
import com.learning.urlshortener.bot.BotTestUtils;

class GlobalCommandsTest extends BaseFullContextTest {

    private final Long CHAT_ID = 666L;

    @Test
    void when_starting_using_bot_then_get_welcome_message() throws Exception {
        //given
        Update update = BotTestUtils.createCommandUpdateWithMessageFromChat(CHAT_ID, "/start");
        update.getMessage().getFrom().setLanguageCode("en");

        //when
        executeUpdate(update);

        //then
        assertThat(executedUpdates.getAllSendMessagesForChatId(CHAT_ID)).hasSize(1);

        String savedMessageText = executedUpdates.getLastSendMessageTextForChatId(CHAT_ID);
        assertThat(savedMessageText).isEqualTo("Hi bro! I can make shortened links for you. See /help for available options.");
    }

    @Test
    void when_requesting_help_should_show_help_response() throws Exception {
        //given
        Update update = BotTestUtils.createCommandUpdateWithMessageFromChat(666L, "/help");
        update.getMessage().getFrom().setLanguageCode("en");

        //when
        executeUpdate(update);

        //then
        assertThat(executedUpdates.getAllSendMessagesForChatId(CHAT_ID)).hasSize(1);

        String savedMessageText = executedUpdates.getLastSendMessageTextForChatId(CHAT_ID);
        assertThat(savedMessageText).contains("Available commands:");
    }
}