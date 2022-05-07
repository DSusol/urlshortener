package com.learning.urlshortener.database.customers;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.learning.urlshortener.domain.Customer;

import lombok.AllArgsConstructor;

@Repository
@Transactional
@AllArgsConstructor
public class CustomerDAOImpl implements CustomerDAO {

    private final CustomerRepository customerRepository;
    private final CustomerEntityMapper customerEntityMapper;

    @Override
    public Customer findCustomerById(Long id) {
        return customerEntityMapper.customerEntityToCustomer(
                customerRepository.getById(id)
        );
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        CustomerEntity savedCustomerEntity = customerRepository.save(
                customerEntityMapper.customerToCustomerEntity(customer)
        );
        return customerEntityMapper.customerEntityToCustomer(savedCustomerEntity);
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }
}