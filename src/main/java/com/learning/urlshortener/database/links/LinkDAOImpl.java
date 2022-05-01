package com.learning.urlshortener.database.links;

import org.springframework.stereotype.Repository;

import com.learning.urlshortener.database.customers.CustomerEntity;
import com.learning.urlshortener.database.customers.CustomerRepository;
import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class LinkDAOImpl implements LinkDAO {

    private final LinkRepository linkRepository;
    private final LinkEntityMapper linkEntityMapper;
    private final CustomerRepository customerRepository;

    @Override
    public Link findLinkById(Long id) {
        return linkEntityMapper.linkEntityToLink(findLinkEntityById(id));
    }

    @Override
    public Link saveLink(Customer customer, Link link) {
        CustomerEntity customerEntity = customerRepository.findById(customer.getId()).orElse(null);
        if (customerEntity == null) {
            //todo: implement exception handling
            throw new RuntimeException("Customer was not found");
        }
        LinkEntity linkEntityToSave = linkEntityMapper.linkToLinkEntity(link);
        linkEntityToSave.setCustomer(customerEntity);

        LinkEntity savedLinkEntity = linkRepository.save(linkEntityToSave);
        customerEntity.getLinks().add(savedLinkEntity);

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
        LinkEntity foundLinkEntity = linkRepository.findById(id).orElse(null);
        if (foundLinkEntity == null) {
            //todo: implement exception handling
            throw new RuntimeException("Link was not found");
        }
        return foundLinkEntity;
    }
}
