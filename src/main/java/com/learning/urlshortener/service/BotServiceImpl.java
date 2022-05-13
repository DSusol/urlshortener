package com.learning.urlshortener.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;

@Service
public class BotServiceImpl implements BotService {

    @Override
    public Link createNewLink(Customer customer) {
        return null;
    }

    @Override
    public Link showLinkById(Long id) {
        return null;
    }

    @Override
    public List<Link> findAllLinksByCustomer(Customer customer) {
        return null;
    }

    @Override
    public void deleteLinkById(Long id) {
    }
}