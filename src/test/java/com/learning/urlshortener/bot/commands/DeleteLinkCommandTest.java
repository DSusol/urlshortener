package com.learning.urlshortener.bot.commands;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.learning.urlshortener.TestContainerSupplier;
import com.learning.urlshortener.bot.UrlShortenerBotRegistrator;

@SpringBootTest
class DeleteLinkCommandTest extends TestContainerSupplier {


    @Autowired
    UrlShortenerBotRegistrator urlShortenerBotRegistrator;

    @Autowired
    AbsSender absSender;

    IBotCommand underTest;
    AbsSender spySender;
    Message preparedMessage;

    @BeforeEach
    void setUp() {
        underTest = urlShortenerBotRegistrator.getRegisteredCommand("delete_link");
        assertThat(underTest).isNotNull();

        spySender = spy(absSender);

        preparedMessage = new Message();
        preparedMessage.setChat(new Chat(572803070L, "private"));
        preparedMessage.setFrom(new User());
    }

    @ParameterizedTest(name = "Run {index}: verified language = {0}")
    @MethodSource("responseMessageArgumentProvider")
    void should_report_deletion_message(String languageCode, String botResponse) throws TelegramApiException {
        //given
        preparedMessage.getFrom().setLanguageCode(languageCode);
        preparedMessage.setText("/delete_link");

        //when
        underTest.processMessage(spySender, preparedMessage, null);

        //then
        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(spySender).execute(argumentCaptor.capture());
        SendMessage sendMessage = argumentCaptor.getValue();

        assertThat(sendMessage.getText()).isEqualTo(botResponse);
    }

    static Stream<Arguments> responseMessageArgumentProvider() {
        return Stream.of(
                arguments("en", "will delete existing link"),
                arguments("ru", "удалит существующий линк"));
    }
}