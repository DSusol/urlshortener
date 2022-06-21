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

import com.learning.urlshortener.bot.utils.MessageUtils;

@SpringBootTest(classes = ShallowAdapterConfig.class)
public class BotMessagesVerificationTest {

    @Autowired
    MessageUtils messageUtils;

    @ParameterizedTest(name = "Run {index}: template = {0}; language = {1}")
    @MethodSource("messageArgumentProvider")
    void i18n_message_verification(String template, String languageCode, String response) {
        LocaleContextHolder.setLocale(Locale.forLanguageTag(languageCode));
        assertThat(messageUtils.getI18nMessageFor(template)).isEqualTo(response);
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
                arguments("new.link.command.description", "en", "Create new link."),
                arguments("new.link.command.description", "ru", "Создать новый линк."),
                arguments("new.link.command.response", "en", "Here is your shortened link:\n"),
                arguments("new.link.command.response", "ru", "Короткий линк:\n"),
                arguments("new.link.command.request.url", "en", "Please provide full link:"),
                arguments("new.link.command.request.url", "ru", "Пожалуйста, введите полный адрес:"),
                arguments("new.link.command.invalid.url", "en", "Invalid url provided, please try again:"),
                arguments("new.link.command.invalid.url", "ru", "Некорректный url, попробуйте ещё раз:"),
                arguments("new.link.command.short.url", "en", "I'm not able to make it shorter."),
                arguments("new.link.command.short.url", "ru", "Не могу сделать ещё короче."),
                arguments("new.link.command.duplicate.url", "en", "Url already exists. Would you like to create new link with the same address (yes/no)?"),
                arguments("new.link.command.duplicate.url", "ru", "Такой адрес уже существует. Хотите создать новый линк с таким же адресом (да/нет)?"),

                // ShowLinkCommand command messages
                arguments("show.link.command.description", "en", "Show link details."),
                arguments("show.link.command.description", "ru", "Свойства линка."),
                arguments("show.link.command.response", "en", "Will show link details."),
                arguments("show.link.command.response", "ru", "Покажет детали линка."),

                // FindAllLinksCommand command messages
                arguments("find.all.links.command.description", "en", "Show all links."),
                arguments("find.all.links.command.description", "ru", "Список всех линков."),
                arguments("find.all.links.command.response", "en", "Will show list of created links."),
                arguments("find.all.links.command.response", "ru", "Покажет список созданных линков."),

                // DeleteLinkCommand command messages
                arguments("delete.links.command.description", "en", "Delete existing link."),
                arguments("delete.links.command.description", "ru", "Удалить линк."),
                arguments("delete.links.command.response", "en", "Will delete existing link."),
                arguments("delete.links.command.response", "ru", "Удалит существующий линк."),

                // Yes/No question
                arguments("yes.answer", "en", "yes"),
                arguments("yes.answer", "ru", "да"),
                arguments("no.answer", "en", "no"),
                arguments("no.answer", "ru", "нет"),
                arguments("unrecognized.answer", "en", "answer is not recognized, please try again:"),
                arguments("unrecognized.answer", "ru", "ответ не распознан, попробуйте ещё раз:"),

                // Exception handling
                arguments("default.exception.message", "en", "Something bad happened. Error message:\n"),
                arguments("default.exception.message", "ru", "Что-то пошло не так. Ошибка:\n")
        );
    }
}
