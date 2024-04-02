package com.ecommerce.api.service.impl;

import com.ecommerce.api.model.Customer;
import com.ecommerce.api.respository.CustomerRepository;
import com.ecommerce.api.service.CustomerService;
import com.ecommerce.api.util.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerServiceImpl implements CustomerService {

    @Autowired
    final private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getUserById(long id) {
        return customerRepository.findById(id);
    }

}
