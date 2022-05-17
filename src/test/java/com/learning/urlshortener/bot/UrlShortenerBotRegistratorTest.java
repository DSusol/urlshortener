package com.learning.urlshortener.bot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.learning.urlshortener.TestContainerSupplier;

@SpringBootTest
class UrlShortenerBotRegistratorTest extends TestContainerSupplier {

    @Autowired
    UrlShortenerBotRegistrator urlShortenerBotRegistrator;

    UrlShortenerBotRegistrator underTest;
    Update preparedUpdate;

    @BeforeEach
    void setUp() {
        underTest = spy(urlShortenerBotRegistrator);

        Message message = new Message();
        message.setChat(new Chat(572803070L, "private"));
        message.setFrom(new User());

        preparedUpdate = new Update();
        preparedUpdate.setMessage(message);
    }

    @Test
    void bot_command_number_verification() {
        assertThat(underTest.getRegisteredCommands()).hasSize(4);
    }

    @ParameterizedTest(name = "Run {index}: verified language = {0}")
    @MethodSource("invalidCommandResponseArgumentProvider")
    void when_sending_invalid_command_should_prompt_help_option(String languageCode, String botResponse)
            throws TelegramApiException {
        //given
        preparedUpdate.getMessage().getFrom().setLanguageCode(languageCode);
        preparedUpdate.getMessage().setText("invalid command");

        //when
        underTest.processNonCommandUpdate(preparedUpdate);

        //then
        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(underTest).execute(argumentCaptor.capture());
        SendMessage sendMessage = argumentCaptor.getValue();

        assertThat(sendMessage.getText()).isEqualTo(botResponse);
    }

    @ParameterizedTest(name = "Run {index}: verified language = {0}")
    @MethodSource("helpCommandResponseArgumentProvider")
    void when_requesting_help_should_show_available_command_options(String languageCode, String botResponse)
            throws TelegramApiException {
        //given
        preparedUpdate.getMessage().getFrom().setLanguageCode(languageCode);
        preparedUpdate.getMessage().setText("/help");

        //when
        underTest.processNonCommandUpdate(preparedUpdate);

        //then
        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(underTest).execute(argumentCaptor.capture());
        SendMessage sendMessage = argumentCaptor.getValue();

        assertThat(sendMessage.getText()).contains(botResponse, "/new_link", "/show_link", "/my_links", "/delete_link");
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
}