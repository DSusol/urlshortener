package com.learning.urlshortener.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.entities.CustomerEntity;

class CustomerMapperTest {

    CustomerMapper mapperUnderTest;

    @BeforeEach
    void setUp() {
        mapperUnderTest = new CustomerMapperImpl();
    }

    @Test
    void customerEntity_to_customer_conversion() {
        //given
        CustomerEntity customerEntity = CustomerEntity.builder().nickname("test name").build();

        //when
        Customer customer = mapperUnderTest.customerEntityToCustomer(customerEntity);

        //then
        assertNotNull(customer);
        assertThat(customer.getNickname()).isEqualTo("test name");
    }

    @Test
    void customer_to_customerEntity_conversion() {
        //given
        Customer customer = Customer.builder().nickname("test name").build();

        //when
        CustomerEntity customerEntity = mapperUnderTest.customerToCustomerEntity(customer);

        //then
        assertNotNull(customerEntity);
        assertThat(customerEntity.getNickname()).isEqualTo("test name");
    }
}