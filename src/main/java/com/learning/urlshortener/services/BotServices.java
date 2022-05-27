package com.learning.urlshortener.services;

import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;

public interface BotServices {

    Customer getCustomerByChatId(Long chatId);

    Customer saveNewCustomer(Long chatId);

    Boolean customerDoesNotExist(Long chatId);

    Link saveNewLink(Customer customer, String url);

    String findUrlByToken(String token);

    String getShortenedUrlByToken(String token);
}
