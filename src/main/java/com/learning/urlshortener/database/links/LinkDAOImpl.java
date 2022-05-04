package com.learning.urlshortener.database.links;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.learning.urlshortener.database.customers.CustomerEntity;
import com.learning.urlshortener.database.customers.CustomerEntityFinder;
import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class LinkDAOImpl implements LinkDAO {

    private final LinkRepository linkRepository;
    private final LinkEntityMapper linkEntityMapper;
    private final CustomerEntityFinder customerEntityFinder;

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
                        customerEntityFinder.findCustomerEntityById(customer.getId()))
                .stream()
                .map(linkEntityMapper::linkEntityToLink)
                .collect(Collectors.toList());
    }

    @Override
    public Link saveLink(Customer customer, Link link) {
        CustomerEntity customerEntity = customerEntityFinder.findCustomerEntityById(customer.getId());

        LinkEntity linkEntityToSave = linkEntityMapper.linkToLinkEntity(link);
        linkEntityToSave.setCustomer(customerEntity);
        LinkEntity savedLinkEntity = linkRepository.save(linkEntityToSave);

        List<LinkEntity> customerLinkEntities = linkRepository.findLinkEntitiesByCustomer(customerEntity);
        customerEntity.setLinks(customerLinkEntities);

        return linkEntityMapper.linkEntityToLink(savedLinkEntity);
    }

    @Override
    public void deleteLinkById(Long id) {
        //todo: implement exception handling
        LinkEntity existingLinkEntity = linkRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Link was not found"));

        CustomerEntity existingCustomerEntity = existingLinkEntity.getCustomer();
        existingCustomerEntity.getLinks().remove(existingLinkEntity);

        linkRepository.delete(existingLinkEntity);
    }
}