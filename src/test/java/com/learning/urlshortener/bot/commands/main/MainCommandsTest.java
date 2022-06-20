package com.learning.urlshortener.bot.commands.main;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.learning.urlshortener.BaseFullContextTest;
import com.learning.urlshortener.bot.BotTestUtils;

public class MainCommandsTest extends BaseFullContextTest {

    // temporary test implementation for the not-yet-incorporated main commands:
    @ParameterizedTest(name = "Run {index}: verified command = {0}")
    @MethodSource("commandArgumentProvider")
    void when_requesting_command_should_show_command_response(String command) {
        //given
        Update update = BotTestUtils.createCommandUpdateWithMessageFromChat(666L, command);

        //when
        executeUpdate(update);

        //then
        assertThat(executedUpdates.getAllSendMessagesForChatId(666L)).hasSize(1);

        String savedMessageText = executedUpdates.getLastSendMessageTextForChatId(666L);
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