package com.ecommerce.api.controller;

import com.ecommerce.api.dto.cart.CartDTO;
import com.ecommerce.api.dto.product.ProductDetailsDTO;
import com.ecommerce.api.dto.user.UserDTO;
import com.ecommerce.api.service.ProductService;
import com.ecommerce.api.util.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;


@RestController
@RequestMapping("api/v1")
@CrossOrigin
public class ProductController {

    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ApiResponse getAllProducts(@RequestParam("categoryName") String categoryName,  @RequestParam("sellerId") String sellerId) {
        return productService.getAllProducts(categoryName, sellerId);
    }

    @GetMapping("/cart/{userId}")
    public ApiResponse getCartDetailsById(@PathVariable Long userId) {
        return productService.getCartDetailsById(userId);
    }

    @PostMapping("/cart/add")
    public ApiResponse updateCart(@RequestBody CartDTO cartDTO) {
        return productService.updateCart(cartDTO);
    }

    @PostMapping("/addProduct")
    public ApiResponse insertProduct(@RequestBody ProductDetailsDTO productDetailsDTO) {
        return productService.insertProduct(productDetailsDTO);
    }

    @GetMapping("/deleteProduct")
    public ApiResponse deleteProduct(@RequestParam("productId") Long productId) {
        return productService.deleteProduct(productId);
    }

    @PostMapping("/cart/remove")
    public ApiResponse removeCartProduct(@RequestBody CartDTO cartDTO) {
        return productService.updateCart(cartDTO);
    }
}
