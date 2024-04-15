package com.ecommerce.api.service.impl;

import com.ecommerce.api.constants.MessageConstant;
import com.ecommerce.api.dto.cart.CartDTO;
import com.ecommerce.api.dto.product.ProductDetailsDTO;
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
import java.math.BigDecimal;
import java.util.*;

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

    public ApiResponse getAllProducts(String categoryName) {
       LOGGER.info("categoryName: " + categoryName);
        List<Product> productDetails = productRepository.getProductDetails(categoryName);
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
                LOGGER.info("cartIds: " + cartIds);
                Optional<ProductDetails> productDetails = productDetailsRepository.getProductById(productId);
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
        Long productId = cartDTO.getProductId();
        if (productId == null) {
            return new ApiResponse(HttpServletResponse.SC_NOT_FOUND, new MessageResponse(MessageConstant.PRODUCT_ID_EMPTY));
        } else {
            Customer customer = customerRepository.getCustomerByUserId(userId);
            List<Long> cartIds = customer.getCart();
            cartIds.add(productId);
            Collections.sort(cartIds);
            String cartId = cartIds.toString();
            int updateCart = productDetailsRepository.updateCart(cartId, userId);
            if (updateCart > 0) {
                return new ApiResponse(HttpServletResponse.SC_OK, new MessageResponse(MessageConstant.ADDED_TO_CART), updateCart);
            } else {
                return new ApiResponse(HttpServletResponse.SC_OK, new MessageResponse(MessageConstant.SOMETHING_WENT_WRONG), updateCart);
            }
        }
    }

    @Override
    public ApiResponse insertProduct(ProductDetailsDTO productDetailsDTO) {
        Long sellerId = productDetailsDTO.getSellerId();
        String name = productDetailsDTO.getProductName();
        String description = productDetailsDTO.getDescription();
        BigDecimal price = productDetailsDTO.getPrice();
        Long categoryId = productDetailsDTO.getCategoryId();
        String image = productDetailsDTO.getImage();
        int insertProduct = productRepository.insertProduct(sellerId, name, description, price, categoryId, image);
        if (insertProduct > 0) {
            return new ApiResponse(HttpServletResponse.SC_OK, new MessageResponse(MessageConstant.ADDED_PRODUCT), insertProduct);
        } else {
            return new ApiResponse(HttpServletResponse.SC_OK, new MessageResponse(MessageConstant.SOMETHING_WENT_WRONG), insertProduct);
        }
    }
}
