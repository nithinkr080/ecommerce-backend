package com.ecommerce.api.util.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse {

    Integer status;
    MessageResponse message;
    List<MessageResponse> errors;
    Object data;

    public ApiResponse(final Integer status, final MessageResponse error) {
        super();
        this.status = status;
        errors = Arrays.asList(error);
    }

    public ApiResponse(final Integer status, final List<MessageResponse> error) {
        super();
        this.status = status;
        this.errors = error;
    }

    public ApiResponse(final Integer status, final MessageResponse message, final Object data) {
        super();
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
