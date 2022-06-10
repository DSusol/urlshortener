package com.learning.urlshortener.services;

import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;
import com.learning.urlshortener.services.urlvalidation.UrlValidationException;

public interface UrlShortenerService {

    Customer getOrCreateCustomerByChatId(Long chatId);

    Link saveNewLink(Customer customer, String url) throws UrlValidationException;

    Link saveValidatedNewLink(Customer customer, String url);

    String findUrlByToken(String token);
}