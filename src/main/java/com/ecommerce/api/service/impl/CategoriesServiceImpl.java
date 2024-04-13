package com.ecommerce.api.service.impl;

import com.ecommerce.api.constants.MessageConstant;
import com.ecommerce.api.model.Category;
import com.ecommerce.api.respository.CategoryRepository;
import com.ecommerce.api.service.CategoryService;
import com.ecommerce.api.util.response.ApiResponse;
import com.ecommerce.api.util.response.MessageResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class CategoriesServiceImpl implements CategoryService {
    private static final Logger LOGGER = LogManager.getLogger(AuthenticateServiceImpl.class);
    @Autowired
    final private CategoryRepository categoryRepository;

    public CategoriesServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ApiResponse getAllCategories() {
        try {
            List<Category> categories = categoryRepository.findAll();
            return new ApiResponse(HttpServletResponse.SC_OK, new MessageResponse(MessageConstant.FETCHED_SUCCESSFULLY), categories);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
