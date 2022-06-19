package com.learning.urlshortener.bot.utils;

import static org.apache.commons.lang3.StringUtils.appendIfMissing;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class WebHookSetter {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    public void setWebHookFor(String botToken, String webhookPath) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httppost = new HttpPost("https://api.telegram.org/bot" + botToken + "/setWebhook");
            List<NameValuePair> params = List.of(new BasicNameValuePair("url", appendIfMissing(webhookPath, "/")));
            httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

            try (CloseableHttpResponse response = httpclient.execute(httppost)) {
                validateWebhookResponse(response);
            }
        }
    }

    @SneakyThrows
    private void validateWebhookResponse(CloseableHttpResponse response) {
        String responseContent = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        ApiResponse<?> result = objectMapper.readValue(responseContent, ApiResponse.class);
        if (!result.getOk()) {
            throw new TelegramApiRequestException("Error setting webhook", result);
        }
    }
}