package com.learning.urlshortener.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;
import com.learning.urlshortener.entities.CustomerEntity;
import com.learning.urlshortener.mappers.CustomerMapper;
import com.learning.urlshortener.mappers.LinkMapper;
import com.learning.urlshortener.repositories.CustomerRepository;

@Service
public class CustomerDaoServiceImpl implements CustomerDaoService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final LinkMapper linkMapper;

    public CustomerDaoServiceImpl(CustomerRepository customerRepository,
                                  CustomerMapper customerMapper, LinkMapper linkMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.linkMapper = linkMapper;
    }


    @Override
    public Customer findCustomerByNickname(String nickname) {
        return customerMapper.customerEntityToCustomer(findCustomerEntityByNickname(nickname));
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        CustomerEntity savedCustomerEntity =
                customerRepository.save(customerMapper.customerToCustomerEntity(customer));
        return customerMapper.customerEntityToCustomer(savedCustomerEntity);
    }

    @Override
    public Customer updateCustomer(Customer customer,  String newNickname) {
        CustomerEntity foundCustomerEntity = findCustomerEntityByNickname(customer.getNickname());
        foundCustomerEntity.setNickname(newNickname);
        return customerMapper.customerEntityToCustomer(customerRepository.save(foundCustomerEntity));
    }

    @Override
    public void deleteCustomer(Customer customer) {
        CustomerEntity customerEntityToDelete = findCustomerEntityByNickname(customer.getNickname());
        customerRepository.delete(customerEntityToDelete);
    }

    @Override
    public List<Link> findAllLinksByCustomer(Customer customer) {
        CustomerEntity foundCustomerEntity = findCustomerEntityByNickname(customer.getNickname());
        return foundCustomerEntity.getLinks().stream()
                .map(linkMapper::linkEntityToLink)
                .collect(Collectors.toList());
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
