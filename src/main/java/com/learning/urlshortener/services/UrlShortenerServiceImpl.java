package com.learning.urlshortener.services;

import static com.learning.urlshortener.services.urlvalidation.UrlValidationExceptionCause.EXISTING_URL;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learning.urlshortener.database.customers.CustomerDAO;
import com.learning.urlshortener.database.links.LinkDAO;
import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;
import com.learning.urlshortener.services.urlvalidation.UrlValidation;
import com.learning.urlshortener.services.urlvalidation.UrlValidationException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UrlShortenerServiceImpl implements UrlShortenerService {

    public static final int URL_TOKEN_LENGTH = 6;

    private final CustomerDAO customerDAO;
    private final LinkDAO linkDAO;
    private final UrlValidation urlValidation;

    @Override
    public Customer getOrCreateCustomerByChatId(Long chatId) {
        Customer customer = customerDAO.findCustomerByChatId(chatId);
        if (customer == null) {
            customer = customerDAO.saveCustomer(Customer.builder().chatId(chatId).build());
        }
        return customer;
    }

    @Override
    public Link saveNewLink(Customer customer, String url) throws UrlValidationException {
        return saveNewLink(customer, url, false);
    }

    @Override
    public Link saveNewLink(Customer customer, String url, boolean duplicateAllowed) throws UrlValidationException {
        urlValidation.validateUrlFor(customer, url);

        boolean duplicateExists = linkDAO.existsLinkEntitiesByCustomerAndUrl(customer, url);
        if (!duplicateAllowed && duplicateExists) {
            throw new UrlValidationException(EXISTING_URL, url + "already exists");
        }

        String token = randomAlphanumeric(URL_TOKEN_LENGTH);
        //todo: verify the token is unique
        Link link = Link.builder().url(url).token(token).clickCount(0).build();
        return linkDAO.saveLink(customer, link);
    }

    @Override
    public String findUrlByToken(String token) {
        Link link = linkDAO.findLinkByToken(token).orElse(null);
        //todo: handle no-link-exception
        if (link == null) {
            throw new IllegalArgumentException("invalid token: " + token);
        }
        return link.getUrl();
    }
}