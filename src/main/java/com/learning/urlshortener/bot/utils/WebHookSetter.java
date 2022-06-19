package com.learning.urlshortener.bot.utils;

import static org.apache.commons.lang3.StringUtils.appendIfMissing;

import java.util.Map;

import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class WebHookSetter {

    @SneakyThrows
    public void setWebHookFor(String botToken, String webhookPath) {

        ApiResponse<?> result = new RestTemplate().postForEntity(
                "https://api.telegram.org/bot" + botToken + "/setWebhook",
                Map.of("url", appendIfMissing(webhookPath, "/")),
                ApiResponse.class).getBody();

        if (result == null || !result.getOk()) {
            throw new TelegramApiRequestException("Error setting webhook.");
        }
    }
}