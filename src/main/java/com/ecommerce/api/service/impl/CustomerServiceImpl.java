package com.ecommerce.api.service.impl;

import com.ecommerce.api.model.Customer;
import com.ecommerce.api.respository.CustomerRepository;
import com.ecommerce.api.service.CustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final Logger LOGGER = LogManager.getLogger(CustomerServiceImpl.class);
    @Autowired
    final private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public Customer getCustomerByUserId(Long id) {
        LOGGER.info("id: " + id.toString());
        return customerRepository.getCustomerByUserId(id);
    }
}
