package com.learning.urlshortener.database.customers;

import org.springframework.stereotype.Repository;

import com.learning.urlshortener.domain.Customer;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class CustomerDAOImpl implements CustomerDAO {

    private final CustomerRepository customerRepository;
    private final CustomerEntityMapper customerEntityMapper;

    @Override
    public Customer findCustomerById(Long id) {
        return customerEntityMapper.customerEntityToCustomer(findCustomerEntityById(id));
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        CustomerEntity savedCustomerEntity = customerRepository.save(
                customerEntityMapper.customerToCustomerEntity(customer)
        );
        return customerEntityMapper.customerEntityToCustomer(savedCustomerEntity);
    }

    @Override
    public Customer updateCustomer(Customer customerToUpdate) {
        if (customerRepository.findById(customerToUpdate.getId()).isEmpty()) {
            //todo: implement exception handling
            throw new RuntimeException("Customer was not found");
        }
        CustomerEntity customerEntityToUpdate = customerEntityMapper.customerToCustomerEntity(customerToUpdate);
        return customerEntityMapper.customerEntityToCustomer(
                customerRepository.save(customerEntityToUpdate)
        );
    }

    @Override
    public void deleteCustomerById(Long id) {
        CustomerEntity customerEntityToDelete = findCustomerEntityById(id);
        customerRepository.delete(customerEntityToDelete);
    }

    private CustomerEntity findCustomerEntityById(Long id) {
        CustomerEntity foundCustomerEntity = customerRepository.findById(id).orElse(null);
        if (foundCustomerEntity == null) {
            //todo: implement exception handling
            throw new RuntimeException("Customer was not found");
        }
        return foundCustomerEntity;
    }
}
