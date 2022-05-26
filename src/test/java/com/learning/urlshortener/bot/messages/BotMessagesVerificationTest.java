package com.learning.urlshortener.bot.messages;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.Locale;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;

import com.learning.urlshortener.bot.utils.MessageHandler;

@SpringBootTest(classes = MessageVerificationConfig.class)
public class BotMessagesVerificationTest {

    @Autowired
    MessageHandler messageHandler;

    @ParameterizedTest(name = "Run {index}: template = {0}; language = {1}")
    @MethodSource("messageArgumentProvider")
    void i18n_message_verification(String template, String languageCode, String response) {
        LocaleContextHolder.setLocale(Locale.forLanguageTag(languageCode));
        assertThat(messageHandler.getI18nMessageFor(template)).isEqualTo(response);
    }

    static Stream<Arguments> messageArgumentProvider() {
        return Stream.of(
                // base bot messages
                arguments("bot.welcome.message", "en", "Hi bro! I can make shortened links for you. See /help for available options."),
                arguments("bot.welcome.message", "ru",
                        "Привет, бро! Давай скоротаем тебе пару линков. Посмотри /help чтобы получить список допустимых команд."),
                arguments("bot.help.header", "en", "<b>Available commands:\n</b>"),
                arguments("bot.help.header", "ru", "<b>Допустимые команды:\n</b>"),
                arguments("bot.default.message", "en", "The command is not recognized. See /help for available options."),
                arguments("bot.default.message", "ru", "Неправильная команда. Введите /help чтобы получить список допустимых команд."),

                // CreateNewLinkCommand command messages
                arguments("new.link.command.description", "en", "create new link"),
                arguments("new.link.command.description", "ru", "создать новый линк"),
                arguments("new.link.command.response", "en", "here is your shortened link:\n"),
                arguments("new.link.command.response", "ru", "короткий линк:\n"),
                arguments("new.link.command.request.url", "en", "Please provide full link:"),
                arguments("new.link.command.request.url", "ru", "Пожалуйста, введите полный адрес:"),

                // ShowLinkCommand command messages
                arguments("show.link.command.description", "en", "show link details"),
                arguments("show.link.command.description", "ru", "свойства линка"),
                arguments("show.link.command.response", "en", "will show link details"),
                arguments("show.link.command.response", "ru", "покажет детали линка"),

                // FindAllLinksCommand command messages
                arguments("find.all.links.command.description", "en", "show all links"),
                arguments("find.all.links.command.description", "ru", "список всех линков"),
                arguments("find.all.links.command.response", "en", "will show list of created links"),
                arguments("find.all.links.command.response", "ru", "покажет список созданных линков"),

                // DeleteLinkCommand command messages
                arguments("delete.links.command.description", "en", "delete existing link"),
                arguments("delete.links.command.description", "ru", "удалить линк"),
                arguments("delete.links.command.response", "en", "will delete existing link"),
                arguments("delete.links.command.response", "ru", "удалит существующий линк")
        );
    }
}
