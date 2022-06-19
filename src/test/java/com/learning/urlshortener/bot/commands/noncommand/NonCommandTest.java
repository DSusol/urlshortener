package com.learning.urlshortener.bot.commands.noncommand;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.learning.urlshortener.BaseFullContextTest;
import com.learning.urlshortener.bot.BotTestUtils;

public class NonCommandTest extends BaseFullContextTest {

    @Test
    void when_sending_unrecognized_command_should_prompt_help_option() throws Exception {
        //given
        Update update = BotTestUtils.createUpdateWithMessageFromChat(666L, "invalid command");

        //when
        executeUpdate(update);

        //then
        assertThat(executedUpdates.getAllSendMessagesForChatId(666L)).hasSize(1);
        String savedMessageText = executedUpdates.getLastSendMessageTextForChatId(666L);
        assertThat(savedMessageText).contains("/help");
    }
}