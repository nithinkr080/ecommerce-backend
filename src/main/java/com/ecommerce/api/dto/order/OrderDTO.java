package com.ecommerce.api.dto.order;

import com.ecommerce.api.dto.cart.CartDTO;
import com.ecommerce.api.dto.product.ProductDetailsDTO;
import com.ecommerce.api.model.ProductDetails;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Validated
public class OrderDTO {

    @JsonProperty
    private String city;
    @JsonProperty
    private String address;
    @JsonProperty
    private List<ProductDetailsDTO> productDetails;
    @JsonProperty
    private Long pincode;
    @JsonProperty
    private String paymentMethod;
    @JsonProperty
    private String orderDate;
    @JsonProperty
    private String deliveryDate;
    @JsonProperty
    private String orderStatus;
}

