package com.learning.urlshortener.services;

import org.springframework.stereotype.Service;

import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;
import com.learning.urlshortener.entities.CustomerEntity;
import com.learning.urlshortener.entities.LinkEntity;
import com.learning.urlshortener.mappers.LinkMapper;
import com.learning.urlshortener.repositories.CustomerRepository;
import com.learning.urlshortener.repositories.LinkRepository;

@Service
public class LinkDaoServiceImpl implements LinkDaoService {

    private final LinkRepository linkRepository;
    private final LinkMapper linkMapper;
    private final CustomerRepository customerRepository;

    public LinkDaoServiceImpl(LinkRepository linkRepository, LinkMapper linkMapper,
                              CustomerRepository customerRepository) {
        this.linkRepository = linkRepository;
        this.linkMapper = linkMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public Link findLinkByShortenedUrl(String shortenedUrl) {
        return linkMapper.linkEntityToLink(findLinkEntityByShortenedUrl(shortenedUrl));
    }

    @Override
    public Link saveLink(Customer customer, Link link) {
        LinkEntity linkEntityToSave = linkMapper.linkToLinkEntity(link);
        CustomerEntity customerEntity = findCustomerEntityByNickname(customer.getNickname());
        linkEntityToSave.setCustomer(customerEntity);

        return linkMapper.linkEntityToLink(linkRepository.save(linkEntityToSave));
    }

    @Override
    public void deleteLink(Link link) {
        LinkEntity foundLinkEntity = findLinkEntityByShortenedUrl(link.getShortenedUrl());
        linkRepository.delete(foundLinkEntity);
    }

    private LinkEntity findLinkEntityByShortenedUrl(String shortenedUrl) {
        LinkEntity foundLinkEntity = linkRepository.findLinkEntityByShortenedUrl(shortenedUrl);
        if (foundLinkEntity == null) {
            //todo: implement exception handling
            throw new RuntimeException("Link " + shortenedUrl + " does not exist");
        }
        return foundLinkEntity;
    }

    private CustomerEntity findCustomerEntityByNickname(String nickname) {
        CustomerEntity foundCustomerEntity = customerRepository.findCustomerEntityByNickname(nickname);
        if (foundCustomerEntity == null) {
            //todo: implement exception handling
            throw new RuntimeException("Customer " + nickname + " does not exist");
        }
        return foundCustomerEntity;
    }
}
