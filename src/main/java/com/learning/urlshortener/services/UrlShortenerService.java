package com.learning.urlshortener.services;

import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;
import com.learning.urlshortener.services.urlvalidation.validators.UrlValidationException;

public interface UrlShortenerService {

    Customer getOrCreateCustomerByChatId(Long chatId);

    Link saveNewLink(Customer customer, String url) throws UrlValidationException;

    String findUrlByToken(String token);
}