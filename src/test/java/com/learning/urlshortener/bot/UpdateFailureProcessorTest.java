package com.learning.urlshortener.bot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.learning.urlshortener.bot.testbot.ExecutedTgTestMethodsRegistry;

import lombok.SneakyThrows;

class UpdateFailureProcessorTest extends ShallowAdapterConfig {

    @Autowired
    ExecutedTgTestMethodsRegistry executedUpdates;

    @Autowired
    UrlShortenerBot underTest;

    @SneakyThrows
    @Test
    void when_sending_update_that_would_cause_unexpected_exception_then_exception_is_handled() {
        //given
        Update newLinkCommandUpdate = BotTestUtils.createCommandUpdateWithMessageFromChat(666L, "/new_link");
        underTest.onUpdatesReceived(List.of(newLinkCommandUpdate));

        Update updateWithNewLink = BotTestUtils.createUpdateWithMessageFromChat(666L, "https://www.test.com/");

        //when
        when(urlShortenerService.saveNewLink(any(), any())).thenThrow(new RuntimeException("Test exception"));
        underTest.onUpdatesReceived(List.of(updateWithNewLink));

        //then
        String savedMessageText = executedUpdates.getLastSendMessageTextForChatId(666L);
        assertThat(savedMessageText).isEqualTo("Something bad happened.");
    }
}