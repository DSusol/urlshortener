package com.learning.urlshortener.bot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

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
        underTest.setBotUserName("Test Bot");
        underTest.getMethods().clear();
    }

    @Test
    void bot_command_number_verification() {
        assertThat(underTest.getRegisteredCommands()).hasSize(4);
    }

    @ParameterizedTest(name = "Run {index}: verified language = {0}")
    @MethodSource("invalidCommandResponseArgumentProvider")
    void when_sending_invalid_command_should_prompt_help_option(String languageCode, String botResponse) {
        //given
        Update update = BotTestUtils.createUpdateWithMessageFromChat(666L, "invalid command", languageCode);

        //when
        underTest.onUpdateReceived(update);

        //then
        assertThat(underTest.getMethods()).hasSize(1);

        BotApiMethod<?> savedMethod = underTest.getMethods().iterator().next();
        assertThat(savedMethod).isInstanceOf(SendMessage.class);
        assertThat(((SendMessage) savedMethod).getText()).isEqualTo(botResponse);
    }

    @ParameterizedTest(name = "Run {index}: verified language = {0}")
    @MethodSource("helpCommandResponseArgumentProvider")
    void when_requesting_help_should_show_available_command_options(String languageCode, String botResponse) {
        //given
        Update update = BotTestUtils.createUpdateWithMessageFromChat(666L, "/help", languageCode);

        //when
        underTest.onUpdateReceived(update);

        //then
        assertThat(underTest.getMethods()).hasSize(1);

        BotApiMethod<?> savedMethod = underTest.getMethods().iterator().next();
        assertThat(savedMethod).isInstanceOf(SendMessage.class);
        assertThat(((SendMessage) savedMethod).getText())
                .contains(botResponse, "/new_link", "/show_link", "/my_links", "/delete_link");
    }

    @ParameterizedTest(name = "Run {index}: verified language = {1}")
    @MethodSource("commandResponseArgumentProvider")
    void when_requesting_command_should_show_command_response(String command, String languageCode, String botResponse) {
        //given
        Update update = BotTestUtils.createCommandUpdateWithMessageFromChat(666L, command, languageCode);

        //when
        underTest.onUpdateReceived(update);

        //then
        assertThat(underTest.getMethods()).hasSize(1);

        BotApiMethod<?> savedMethod = underTest.getMethods().iterator().next();
        assertThat(savedMethod).isInstanceOf(SendMessage.class);
        assertThat(((SendMessage) savedMethod).getText()).isEqualTo(botResponse);
    }

    static Stream<Arguments> invalidCommandResponseArgumentProvider() {
        return Stream.of(
                arguments("en", "The command is not recognized. See /help for available options."),
                arguments("ru", "Неправильная команда. Введите /help чтобы получить список допустимых команд."));
    }

    static Stream<Arguments> helpCommandResponseArgumentProvider() {
        return Stream.of(
                arguments("en", "Available commands:"),
                arguments("ru", "Допустимые команды:"));
    }

    static Stream<Arguments> commandResponseArgumentProvider() {
        return Stream.of(
                arguments("/new_link", "en", "will create new link"),
                arguments("/new_link", "ru", "создаст новый линк"),
                arguments("/show_link", "en", "will show link details"),
                arguments("/show_link", "ru", "покажет детали линка"),
                arguments("/my_links", "en", "will show list of created links"),
                arguments("/my_links", "ru", "покажет список созданных линков"),
                arguments("/delete_link", "en", "will delete existing link"),
                arguments("/delete_link", "ru", "удалит существующий линк")
        );
    }
}