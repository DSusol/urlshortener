package com.learning.urlshortener.database.customers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.learning.urlshortener.domain.Customer;

class CustomerEntityMapperTest {

    CustomerEntityMapper underTest;

    @BeforeEach
    void setUp() {
        underTest = CustomerEntityMapper.INSTANCE;
    }

    @Test
    void customerEntity_to_customer_conversion() {
        //given
        CustomerEntity customerEntity = CustomerEntity.builder().nickname("test name").build();

        //when
        Customer customer = underTest.customerEntityToCustomer(customerEntity);

        //then
        assertNotNull(customer);
        assertThat(customer.getNickname()).isEqualTo("test name");
    }

    @Test
    void customer_to_customerEntity_conversion() {
        //given
        Customer customer = Customer.builder().nickname("test name").build();

        //when
        CustomerEntity customerEntity = underTest.customerToCustomerEntity(customer);

        //then
        assertNotNull(customerEntity);
        assertThat(customerEntity.getNickname()).isEqualTo("test name");
    }
}