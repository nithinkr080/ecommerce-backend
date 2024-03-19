package com.ecommerce.api.service;

import com.ecommerce.api.dto.user.UserDTO;
import com.ecommerce.api.model.User;
import com.ecommerce.api.util.response.ApiResponse;

import java.util.List;

public interface UserService {
   User getByUserEmailId(UserDTO userDTO);

   ApiResponse login(UserDTO userDTO);

}
