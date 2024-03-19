package com.ecommerce.api.service;

import com.ecommerce.api.dto.user.UserDTO;
import com.ecommerce.api.model.User;
import com.ecommerce.api.util.response.ApiResponse;

public interface AuthenticateService {
   User getByUserEmailId(UserDTO userDTO);

   ApiResponse signIn(UserDTO userDTO);

   ApiResponse signUp(UserDTO userDTO);

}
