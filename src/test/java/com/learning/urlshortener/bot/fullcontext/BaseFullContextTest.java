package com.learning.urlshortener.bot.fullcontext;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.learning.urlshortener.TestContainerSupplier;
import com.learning.urlshortener.bot.testbot.ExecutedTgTestMethodsRegistry;
import com.learning.urlshortener.bot.testbot.UrlShortenerTestBot;

@SpringBootTest
public class BaseFullContextTest extends TestContainerSupplier {

    @Autowired
    UrlShortenerTestBot underTest;

    @Autowired
    ExecutedTgTestMethodsRegistry executedUpdates;

    @BeforeEach
    protected void setUp() {
        executedUpdates.clearAllSendMessages();
    }

    protected void executeUpdate(Update update) {
        underTest.onUpdatesReceived(List.of(update));
    }
}
