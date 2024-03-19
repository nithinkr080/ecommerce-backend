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
public class UserDTO {
    @JsonProperty
    private String emailId;
    @JsonProperty
    private  String password;
    @JsonProperty
    private String username;

}


