package com.ecommerce.api.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Validated
public class ProductDTO {
    @JsonProperty
    private Long userId;
    @JsonProperty
    private String username;
    @JsonProperty
    private String email;
    @JsonProperty
    private String password;
    @JsonProperty
    private String role;
}
