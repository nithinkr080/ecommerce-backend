package com.ecommerce.api.service.impl;

import com.ecommerce.api.constants.MessageConstant;
import com.ecommerce.api.dto.cart.CartDTO;
import com.ecommerce.api.model.Customer;
import com.ecommerce.api.model.Product;
import com.ecommerce.api.model.ProductDetails;
import com.ecommerce.api.respository.CustomerRepository;
import com.ecommerce.api.respository.ProductDetailsRepository;
import com.ecommerce.api.respository.ProductRepository;
import com.ecommerce.api.service.ProductService;
import com.ecommerce.api.util.response.ApiResponse;
import com.ecommerce.api.util.response.MessageResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger LOGGER = LogManager.getLogger(AuthenticateServiceImpl.class);
    @Autowired
    final private ProductRepository productRepository;
    @Autowired
    final private CustomerRepository customerRepository;

    @Autowired
    final private ProductDetailsRepository productDetailsRepository;

    public ProductServiceImpl(ProductRepository productRepository, CustomerRepository customerRepository, ProductDetailsRepository productDetailsRepository) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.productDetailsRepository = productDetailsRepository;
    }

    public ApiResponse getAllProducts() {
        List<Product> productDetails = productRepository.getProductDetails();
        return new ApiResponse(HttpServletResponse.SC_OK, new MessageResponse(MessageConstant.PRODUCT_LIST_SUCCESSFULL), productDetails);
    }

    public ApiResponse getCartDetailsById(Long userId) {
        Customer customer = customerRepository.getCustomerByUserId(userId);
        if (customer == null) {
            return new ApiResponse(HttpServletResponse.SC_NOT_FOUND, new MessageResponse(MessageConstant.USER_NOT_FOUND));
        } else {
            List<Long> cartIds = customer.getCart();
            List<ProductDetails> cartList = new ArrayList<>();
            for (Long productId : cartIds) {
                Optional<ProductDetails> productDetails = productDetailsRepository.getProductById(productId.longValue());
                productDetails.ifPresent(cartList::add);
            }
            if (cartList.isEmpty()) {
                return new ApiResponse(HttpServletResponse.SC_OK, new MessageResponse(MessageConstant.CART_LIST_EMPTY), cartList);
            } else {
                return new ApiResponse(HttpServletResponse.SC_OK, new MessageResponse(MessageConstant.CART_LIST_SUCCESSFULL), cartList);
            }
        }
    }

    @Override
    public ApiResponse updateCart(CartDTO cartDTO) {
        Long userId = cartDTO.getUserId();
        Customer customer = customerRepository.getCustomerByUserId(userId);
        List<Long> cartIds = customer.getCart();
        cartIds.add(cartDTO.getProductId());
        String cartId = cartIds.toString();
        int updateCart = productDetailsRepository.updateCart(cartId, userId);
        if (updateCart > 0) {
            return new ApiResponse(HttpServletResponse.SC_OK, new MessageResponse(MessageConstant.ADDED_TO_CART));
        } else {
            return new ApiResponse(HttpServletResponse.SC_OK, new MessageResponse(MessageConstant.SOMETHING_WENT_WRONG));
        }
    }
}
