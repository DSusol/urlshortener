package com.learning.urlshortener.database.links;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.learning.urlshortener.database.customers.CustomerEntity;
import com.learning.urlshortener.database.customers.CustomerRepository;
import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;

import lombok.AllArgsConstructor;

@Repository
@Transactional
@AllArgsConstructor
public class LinkDAOImpl implements LinkDAO {

    private final LinkRepository linkRepository;
    private final CustomerRepository customerRepository;
    private final LinkEntityMapper linkEntityMapper;

    @Override
    public Optional<Link> findLinkById(Long id) {
        return linkRepository.findById(id).map(linkEntityMapper::linkEntityToLink);
    }

    @Override
    public Optional<Link> findLinkByShortenedUrl(String shortenedUrl) {
        return linkRepository.findLinkEntityByShortenedUrl(shortenedUrl).map(linkEntityMapper::linkEntityToLink);
    }

    @Override
    public List<Link> findAllLinksByCustomer(Customer customer) {
        return linkRepository.findLinkEntitiesByCustomer(
                        customerRepository.getById(customer.getId()))
                .stream()
                .map(linkEntityMapper::linkEntityToLink)
                .collect(Collectors.toList());
    }

    @Override
    public Link saveLink(Customer customer, Link link) {
        CustomerEntity customerEntity = customerRepository.getById(customer.getId());

        LinkEntity linkEntityToSave = linkEntityMapper.linkToLinkEntity(link);
        linkEntityToSave.setCustomer(customerEntity);
        LinkEntity savedLinkEntity = linkRepository.save(linkEntityToSave);

        return linkEntityMapper.linkEntityToLink(savedLinkEntity);
    }

    @Override
    public void deleteLinkById(Long id) {
        linkRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        linkRepository.deleteAll();
    }
}