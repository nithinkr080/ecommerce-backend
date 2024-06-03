package com.ecommerce.api.service.impl;

import com.ecommerce.api.constants.MessageConstant;
import com.ecommerce.api.dto.cart.CartDTO;
import com.ecommerce.api.dto.order.OrderDTO;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
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

    public ApiResponse getAllProducts(String categoryName, String sellerId) {
        String stringifySellerId = String.valueOf(sellerId);
        List<Product> productDetails = productRepository.getProductDetails(categoryName, stringifySellerId);
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
        try {
            String type = cartDTO.getType();
            LOGGER.info("type: " + type);
            if (Objects.equals(type, "add")) {
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
            } else {
                String cartList = cartDTO.getCartList();
                Long userId = cartDTO.getUserId();
                int removeCartProduct = productRepository.removeCartProduct(userId, cartList);
                if (removeCartProduct > 0) {
                    return new ApiResponse(HttpServletResponse.SC_OK, new MessageResponse(MessageConstant.REMOVED_FROM_CART), removeCartProduct);
                } else {
                    return new ApiResponse(HttpServletResponse.SC_OK, new MessageResponse(MessageConstant.SOMETHING_WENT_WRONG), removeCartProduct);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
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

    @Override
    public ApiResponse deleteProduct(Long productId) {
        LOGGER.info("productId: " + productId);
        int deleteProduct = productRepository.deleteProduct(productId);
        if (deleteProduct > 0) {
            return new ApiResponse(HttpServletResponse.SC_OK, new MessageResponse(MessageConstant.DELETED_PRODUCT), deleteProduct);
        } else {
            return new ApiResponse(HttpServletResponse.SC_OK, new MessageResponse(MessageConstant.SOMETHING_WENT_WRONG), deleteProduct);
        }
    }

    @Override
    public ApiResponse updateOrder(CartDTO cartDTO) {
        try {
            Long userId = cartDTO.getUserId();
            List<OrderDTO> currentOrderDetails = cartDTO.getOrderDetails();
            String existingOrderDetailsJson = productRepository.getOrders(userId);
            LOGGER.info("existingOrderDetailsJson: " + existingOrderDetailsJson);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                List<OrderDTO> orderList;
                if (existingOrderDetailsJson != null) {
                    orderList = objectMapper.readValue(existingOrderDetailsJson, new TypeReference<List<OrderDTO>>() {
                    });
                    orderList.addAll(currentOrderDetails);
                } else {
                    orderList = currentOrderDetails;
                }
                String orderListJson = objectMapper.writeValueAsString(orderList);
                int updateOrder = productRepository.updateOrder(userId, orderListJson);
                if (updateOrder > 0) {
                    return new ApiResponse(HttpServletResponse.SC_OK, new MessageResponse(MessageConstant.PLACED_ORDER), updateOrder);
                } else {
                    return new ApiResponse(HttpServletResponse.SC_OK, new MessageResponse(MessageConstant.SOMETHING_WENT_WRONG), updateOrder);
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ApiResponse getOrders(Long userId) {
        try {
            String existingOrderDetailsJson = productRepository.getOrders(userId);
            ObjectMapper objectMapper = new ObjectMapper();
            List<OrderDTO> orderList = objectMapper.readValue(existingOrderDetailsJson, new TypeReference<List<OrderDTO>>() {
            });
            for (OrderDTO order : orderList) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("d/MM/yyyy");
                LocalDate today = LocalDate.now();
                try {
                    Date date = dateFormat.parse(order.getDeliveryDate());
                    LocalDate parsedDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    if (parsedDate.equals(today)) {
                        order.setOrderStatus("Delivered");
                        String orderListJson = objectMapper.writeValueAsString(orderList);
                        productRepository.updateOrder(userId, orderListJson);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (existingOrderDetailsJson.isEmpty()) {
                return new ApiResponse(HttpServletResponse.SC_OK, new MessageResponse(MessageConstant.SOMETHING_WENT_WRONG), existingOrderDetailsJson);
            } else {
                return new ApiResponse(HttpServletResponse.SC_OK, new MessageResponse(MessageConstant.SUCCESS), existingOrderDetailsJson);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ApiResponse cancelOrder(String orderId, Long userId) {
        try {
            String existingOrderDetailsJson = productRepository.getOrders(userId);
            if (existingOrderDetailsJson == null || existingOrderDetailsJson.isEmpty()) {
                return new ApiResponse(HttpServletResponse.SC_OK, new MessageResponse(MessageConstant.SOMETHING_WENT_WRONG), null);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            List<OrderDTO> orderList = objectMapper.readValue(existingOrderDetailsJson, new TypeReference<List<OrderDTO>>() {
            });

            boolean orderFound = false;
            for (Iterator<OrderDTO> iterator1 = orderList.iterator(); iterator1.hasNext(); ) {
                OrderDTO order = iterator1.next();
                List<ProductDetailsDTO> productDetails = order.getProductDetails();
                for (Iterator<ProductDetailsDTO> iterator = productDetails.iterator(); iterator.hasNext(); ) {
                    ProductDetailsDTO productDetail = iterator.next();
                    if (Objects.equals(orderId, productDetail.getOrderId())) {
                        LOGGER.info("Removing productDetails: " + productDetail);
                        iterator.remove();
                        orderFound = true;
                        if (productDetails.isEmpty()) {
                            iterator1.remove();
                        }

                    }
                }
            }

            if (orderFound) {
                String updatedOrderDetailsJson = objectMapper.writeValueAsString(orderList);
                productRepository.updateOrder(userId, updatedOrderDetailsJson);
                return new ApiResponse(HttpServletResponse.SC_OK, new MessageResponse(MessageConstant.CANCEL_ORDER_SUCCESS), null);
            }
            return new ApiResponse(HttpServletResponse.SC_OK, new MessageResponse(MessageConstant.SOMETHING_WENT_WRONG), null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
