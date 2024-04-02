package com.ecommerce.api.service.impl;

import com.ecommerce.api.constants.MessageConstant;
import com.ecommerce.api.dto.product.ProductDetailsDTO;
import com.ecommerce.api.model.Product;
import com.ecommerce.api.respository.ProductRepository;
import com.ecommerce.api.service.ProductService;
import com.ecommerce.api.util.response.ApiResponse;
import com.ecommerce.api.util.response.MessageResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger LOGGER = LogManager.getLogger(AuthenticateServiceImpl.class);
    @Autowired
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ApiResponse getAllProducts() {
        List<Product> productDetails = productRepository.getProductDetails();
        return new ApiResponse(HttpServletResponse.SC_OK, new MessageResponse(MessageConstant.PRODUCT_LIST_SUCCESSFULL), productDetails);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id);
    }
}
