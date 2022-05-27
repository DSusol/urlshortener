package com.learning.urlshortener.bot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.objects.Update;

@SpringBootTest
class UrlShortenerBotTest extends BaseFullContextTest {

    private final Long CHAT_ID = 666L;

    @Test
    void when_starting_using_bot_then_get_welcome_message() {
        //given
        Update update = BotTestUtils.createUpdateWithMessageFromChat(CHAT_ID, "/start");
        update.getMessage().getFrom().setLanguageCode("en");

        //when
        executeUpdate(update);

        //then
        assertThat(executedUpdates.getAllSendMessagesForChatId(CHAT_ID)).hasSize(1);

        String savedMessageText = executedUpdates.getLastSendMessageTextForChatId(CHAT_ID);
        assertThat(savedMessageText).isEqualTo("Hi bro! I can make shortened links for you. See /help for available options.");
    }

    @Test
    void when_sending_invalid_command_should_prompt_help_option() {
        //given
        Update update = BotTestUtils.createUpdateWithMessageFromChat(666L, "invalid command");

        //when
        executeUpdate(update);

        //then
        assertThat(executedUpdates.getAllSendMessagesForChatId(CHAT_ID)).hasSize(1);
        String savedMessageText = executedUpdates.getLastSendMessageTextForChatId(CHAT_ID);
        assertThat(savedMessageText).contains("/help");
    }

    @Test
    void when_requesting_help_should_show_help_response() {
        //given
        Update update = BotTestUtils.createUpdateWithMessageFromChat(666L, "/help");
        update.getMessage().getFrom().setLanguageCode("en");

        //when
        executeUpdate(update);

        //then
        assertThat(executedUpdates.getAllSendMessagesForChatId(CHAT_ID)).hasSize(1);

        String savedMessageText = executedUpdates.getLastSendMessageTextForChatId(CHAT_ID);
        assertThat(savedMessageText).contains("Available commands:");
    }

    // temporary test implementation for the not-yet-incorporated commands:
    @ParameterizedTest(name = "Run {index}: verified command = {0}")
    @MethodSource("commandArgumentProvider")
    void when_requesting_command_should_show_command_response(String command) {
        //given
        Update update = BotTestUtils.createCommandUpdateWithMessageFromChat(CHAT_ID, command);

        //when
        executeUpdate(update);

        //then
        assertThat(executedUpdates.getAllSendMessagesForChatId(CHAT_ID)).hasSize(1);

        String savedMessageText = executedUpdates.getLastSendMessageTextForChatId(CHAT_ID);
        assertThat(savedMessageText).doesNotContain("/help");
    }

    static Stream<Arguments> commandArgumentProvider() {
        return Stream.of(
                arguments("/show_link"),
                arguments("/my_links"),
                arguments("/delete_link")
        );
    }
}