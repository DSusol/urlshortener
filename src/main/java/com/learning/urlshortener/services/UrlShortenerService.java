package com.learning.urlshortener.services;

import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;
import com.learning.urlshortener.services.urlvalidation.UrlValidationException;

public interface UrlShortenerService {

    Customer getOrCreateCustomerByChatId(Long chatId);

    Link saveNewLink(Customer customer, String url) throws UrlValidationException;

    Link saveNewLink(Customer customer, String url, boolean duplicateAllowed) throws UrlValidationException;

    String findUrlByToken(String token);
}