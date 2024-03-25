package com.ecommerce.api.controller;

import com.ecommerce.api.dto.user.ProductDTO;
import com.ecommerce.api.dto.user.UserDTO;
import com.ecommerce.api.model.User;
import com.ecommerce.api.service.AuthenticateService;
import com.ecommerce.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@CrossOrigin
public class ProductController {

    @Autowired
    final private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public User getAllProducts(@RequestBody ProductDTO productDTO) {
        return productService.getAllProducts(productDTO);
    }
}
