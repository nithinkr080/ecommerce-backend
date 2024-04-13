package com.ecommerce.api.controller;

import com.ecommerce.api.model.Category;
import com.ecommerce.api.service.CategoryService;
import com.ecommerce.api.service.CustomerService;
import com.ecommerce.api.util.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@CrossOrigin
public class CategoriesController {

    @Autowired
    final private CategoryService categoryService;

    public CategoriesController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    ApiResponse getAllCategories() {
        return categoryService.getAllCategories();
    }
}
