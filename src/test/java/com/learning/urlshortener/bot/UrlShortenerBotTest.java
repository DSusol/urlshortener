package com.learning.urlshortener.bot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.learning.urlshortener.TestContainerSupplier;

@SpringBootTest
class UrlShortenerBotTest extends TestContainerSupplier {

    @Autowired
    UrlShortenerTestBot underTest;

    @BeforeEach
    void botSetUp() {
        underTest.getExecutedMethods().clear();
    }

    @Test
    void when_sending_invalid_command_should_prompt_help_option() {
        //given
        Update update = BotTestUtils.createUpdateWithMessageFromChat(666L, "invalid command");

        //when
        underTest.onUpdatesReceived(List.of(update));

        //then
        assertThat(underTest.getExecutedMethods()).hasSize(1);

        BotApiMethod<?> savedMethod = underTest.getExecutedMethods().iterator().next();
        assertThat(savedMethod).isInstanceOf(SendMessage.class);
        assertThat(((SendMessage) savedMethod).getChatId()).isEqualTo("666");
        assertThat(((SendMessage) savedMethod).getText()).contains("/help");
    }

    @ParameterizedTest(name = "Run {index}: verified language = {0}")
    @MethodSource("helpCommandResponseArgumentProvider")
    void when_requesting_help_should_show_available_i18n_response(String languageCode, String botResponse) {
        //given
        Update update = BotTestUtils.createUpdateWithMessageFromChat(666L, "/help");
        update.getMessage().getFrom().setLanguageCode(languageCode);

        //when
        underTest.onUpdatesReceived(List.of(update));

        //then
        assertThat(underTest.getExecutedMethods()).hasSize(1);

        BotApiMethod<?> savedMethod = underTest.getExecutedMethods().iterator().next();
        assertThat(savedMethod).isInstanceOf(SendMessage.class);
        assertThat(((SendMessage) savedMethod).getChatId()).isEqualTo("666");
        assertThat(((SendMessage) savedMethod).getText()).contains(botResponse);
    }

    @ParameterizedTest(name = "Run {index}: verified command = {0}")
    @MethodSource("commandArgumentProvider")
    void when_requesting_command_should_show_command_response(String command) {
        //given
        Update update = BotTestUtils.createCommandUpdateWithMessageFromChat(666L, command);

        //when
        underTest.onUpdatesReceived(List.of(update));

        //then
        assertThat(underTest.getExecutedMethods()).hasSize(1);

        BotApiMethod<?> savedMethod = underTest.getExecutedMethods().iterator().next();
        assertThat(savedMethod).isInstanceOf(SendMessage.class);
        assertThat(((SendMessage) savedMethod).getChatId()).isEqualTo("666");
        assertThat(((SendMessage) savedMethod).getText()).doesNotContain("/help");
    }

    static Stream<Arguments> helpCommandResponseArgumentProvider() {
        return Stream.of(
                arguments("en", "Available commands:"),
                arguments("ru", "Допустимые команды:"));
    }

    static Stream<Arguments> commandArgumentProvider() {
        return Stream.of(
                arguments("/new_link"),
                arguments("/show_link"),
                arguments("/my_links"),
                arguments("/delete_link")
        );
    }
}