package com.ecommerce.api.dto.cart;

import com.ecommerce.api.dto.order.OrderDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartDTO {
    @JsonProperty
    private Long userId;

    @JsonProperty
    private String type;

    @JsonProperty
    private Long productId;

    @JsonProperty
    private String cartList;

    @JsonProperty
    private List<OrderDTO> orderDetails;
}
