package com.learning.urlshortener.services;

import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;

public interface UrlShortenerService {

    Customer getOrCreateCustomerByChatId(Long chatId);

    Link saveNewLink(Customer customer, String url);

    String findUrlByToken(String token);

    String getShortenedUrlByToken(String token);
}
