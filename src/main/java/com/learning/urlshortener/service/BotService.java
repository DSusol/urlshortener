package com.learning.urlshortener.service;

import java.util.List;

import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;

public interface BotService {

    Link createNewLink(Customer customer);

    Link showLinkById(Long id);

    List<Link> findAllLinksByCustomer(Customer customer);

    void deleteLinkById(Long id);
}