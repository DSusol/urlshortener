package com.learning.urlshortener.database.customers;

import org.springframework.stereotype.Repository;

import com.learning.urlshortener.domain.Customer;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class CustomerDAOImpl implements CustomerDAO {

    private final CustomerRepository customerRepository;
    private final CustomerEntityMapper customerEntityMapper;
    private final CustomerFinder customerFinder;

    @Override
    public Customer findCustomerById(Long id) {
        return customerEntityMapper.customerEntityToCustomer(customerFinder.findCustomerEntityById(id));
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
        CustomerEntity customerEntityToDelete = customerFinder.findCustomerEntityById(id);
        customerRepository.delete(customerEntityToDelete);
    }
}