package com.learning.urlshortener.database.links;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.learning.urlshortener.database.customers.CustomerEntity;
import com.learning.urlshortener.database.customers.CustomerFinder;
import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class LinkDAOImpl implements LinkDAO {

    private final LinkRepository linkRepository;
    private final LinkEntityMapper linkEntityMapper;
    private final CustomerFinder customerFinder;

    @Override
    public Link findLinkById(Long id) {
        return linkEntityMapper.linkEntityToLink(findLinkEntityById(id));
    }

    @Override
    public Link findLinkByShortenedUrl(String shortenedUrl) {
        LinkEntity foundLinkEntity = linkRepository.findLinkEntityByShortenedUrl(shortenedUrl)
                .orElseThrow(() -> new RuntimeException("Link was not found for provided shortenedUrl"));
        return linkEntityMapper.linkEntityToLink(foundLinkEntity);
    }

    @Override
    public List<Link> findAllLinksByCustomer(Customer customer) {
        return linkRepository.findLinkEntitiesByCustomer(
                        customerFinder.findCustomerEntityById(customer.getId()))
                .stream()
                .map(linkEntityMapper::linkEntityToLink)
                .collect(Collectors.toList());
    }

    @Override
    public Link saveLink(Customer customer, Link link) {
        CustomerEntity customerEntity = customerFinder.findCustomerEntityById(customer.getId());

        LinkEntity linkEntityToSave = linkEntityMapper.linkToLinkEntity(link);
        linkEntityToSave.setCustomer(customerEntity);
        LinkEntity savedLinkEntity = linkRepository.save(linkEntityToSave);

        List<LinkEntity> customerLinkEntities = linkRepository.findLinkEntitiesByCustomer(customerEntity);
        customerEntity.setLinks(customerLinkEntities);

        return linkEntityMapper.linkEntityToLink(savedLinkEntity);
    }

    @Override
    public void deleteLinkById(Long id) {
        LinkEntity foundLinkEntity = findLinkEntityById(id);
        CustomerEntity foundCustomerEntity = foundLinkEntity.getCustomer();
        foundCustomerEntity.getLinks().remove(foundLinkEntity);
        linkRepository.delete(foundLinkEntity);
    }

    private LinkEntity findLinkEntityById(Long id) {
        //todo: implement exception handling
        return linkRepository.findById(id).orElseThrow(() -> new RuntimeException("Link was not found"));
    }
}