package com.ecommerce.api.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDetailsDTO {
    @JsonProperty
    private Long productId;
    @JsonProperty
    private String image;
    @JsonProperty
    private String productName;
    @JsonProperty
    private String description;
    @JsonProperty
    private BigDecimal price;
    @JsonProperty
    private Long categoryId;
    @JsonProperty
    private String categoryName;
    @JsonProperty
    private Long sellerId;
    @JsonProperty
    private String companyName;
    @JsonProperty
    private Long userId;
    @JsonProperty
    private String sellerUsername;
    @JsonProperty
    private String sellerEmail;
    @JsonProperty
    private String quantity;
    @JsonProperty
    private String orderId;
}