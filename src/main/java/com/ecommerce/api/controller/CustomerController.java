package com.ecommerce.api.controller;

import com.ecommerce.api.model.Customer;
import com.ecommerce.api.service.CustomerService;
import com.ecommerce.api.util.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@CrossOrigin
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customer/{id}")
    public Customer getCustomerByUserId(@PathVariable Long id) {
        return customerService.getCustomerByUserId(id);
    }
}
