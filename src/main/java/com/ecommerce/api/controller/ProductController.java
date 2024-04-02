package com.ecommerce.api.controller;

import com.ecommerce.api.dto.product.ProductDetailsDTO;
import com.ecommerce.api.model.Product;
import com.ecommerce.api.service.ProductService;
import com.ecommerce.api.util.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@CrossOrigin
public class ProductController {

    @Autowired
    final private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ApiResponse getAllProducts() {
        return productService.getAllProducts();
    }
}
