package com.learning.urlshortener.bot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MessageVerificationConfig.class})
public class BotMessagesVerificationTest {


    @Autowired
    UrlShortenerTestBot underTest;

    @BeforeEach
    void botSetUp() {
        underTest.getExecutedMethods().clear();
    }

    @ParameterizedTest(name = "Run {index}: verified language = {0}")
    @MethodSource("commandResponseArgumentProvider")
    void command_i18n_response_verification(String command, String languageCode, String response) {

        //given
        Update update = BotTestUtils.createCommandUpdateWithMessageFromChat(666L, command);
        update.getMessage().getFrom().setLanguageCode(languageCode);

        //when
        underTest.onUpdatesReceived(List.of(update));

        //then
        assertThat(underTest.getExecutedMethods()).hasSize(1);

        BotApiMethod<?> savedMethod = underTest.getExecutedMethods().iterator().next();
        assertThat(savedMethod).isInstanceOf(SendMessage.class);
        assertThat(((SendMessage) savedMethod).getText()).isEqualTo(response);
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
