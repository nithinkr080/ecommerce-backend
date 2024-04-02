package com.ecommerce.api.dto.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Validated
public class CartDTO {
    @JsonProperty
    private Long userId;
    
    @JsonProperty
    private Long ProductId;
}
