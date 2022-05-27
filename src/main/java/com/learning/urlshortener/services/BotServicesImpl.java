package com.learning.urlshortener.services;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

import org.springframework.stereotype.Service;

import com.learning.urlshortener.bot.utils.DomainProvider;
import com.learning.urlshortener.database.customers.CustomerDAO;
import com.learning.urlshortener.database.links.LinkDAO;
import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BotServicesImpl implements BotServices {

    private final CustomerDAO customerDAO;
    private final LinkDAO linkDAO;
    private final DomainProvider domainProvider;

    @Override
    public Customer getCustomerByChatId(Long chatId) {
        return customerDAO.findCustomerByChatId(chatId);
    }

    @Override
    public Customer saveNewCustomer(Long chatId) {
        return customerDAO.saveCustomer(Customer.builder().chatId(chatId).build());
    }

    @Override
    public Boolean customerDoesNotExist(Long chatId) {
        return customerDAO.findCustomerByChatId(chatId) == null;
    }

    @Override
    public Link saveNewLink(Customer customer, String url) {
        String token = randomAlphanumeric(6);
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

    @Override
    public String getShortenedUrlByToken(String token) {
        return domainProvider.getDomain() + token;
    }
}