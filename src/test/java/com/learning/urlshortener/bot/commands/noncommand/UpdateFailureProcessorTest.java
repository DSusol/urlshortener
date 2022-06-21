package com.learning.urlshortener.bot.commands.noncommand;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.learning.urlshortener.BaseFullContextTest;
import com.learning.urlshortener.bot.BotTestUtils;

class UpdateFailureProcessorTest extends BaseFullContextTest {

    @Test
    void when_sending_update_that_would_cause_exception_then_exception_is_handled() {
        //given
        Update newLinkUpdate = BotTestUtils.createCommandUpdateWithMessageFromChat(666L, "/new_link");
        executeUpdate(newLinkUpdate);

        Update updateWithVeryLongUrl = BotTestUtils.createCommandUpdateWithMessageFromChat(666L,
                "https://www.test.com/" + "will_violate_database_restriction_for_url_length".repeat(99));

        //when
        executeUpdate(updateWithVeryLongUrl);

        //then
        String savedMessageText = executedUpdates.getLastSendMessageTextForChatId(666L);
        assertThat(savedMessageText).contains("Something bad happened. Error message:");
    }
}