package com.ecommerce.api.service.impl;

import com.ecommerce.api.constants.MessageConstant;
import com.ecommerce.api.dto.user.UserDTO;
import com.ecommerce.api.model.User;
import com.ecommerce.api.respository.UserRepository;
import com.ecommerce.api.service.UserService;
import com.ecommerce.api.util.response.ApiResponse;
import com.ecommerce.api.util.response.MessageResponse;
import lombok.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public class AuthenticateServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(AuthenticateServiceImpl.class);
    @Autowired
    final private UserRepository userRepository;

    public AuthenticateServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getByUserEmailId(@NotNull UserDTO userDTO) {
        String email = userDTO.getEmailId();
        LOGGER.info("email: " + email);
        return userRepository.getByUserEmailId(email);
    }



    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    private static class UserDetails {
        private String email;
        private String username;
    }

    @Override
    public ApiResponse login(UserDTO userDTO) {
        String email = userDTO.getEmailId();
        String password = userDTO.getPassword();
        if ((email == null || password == null || email.isEmpty()) || password.isEmpty()) {
            String emptyMessage = email == null || email.isEmpty() ? MessageConstant.EMPTY_EMAIL : MessageConstant.EMPTY_PASSWORD;
            return new ApiResponse(HttpServletResponse.SC_BAD_REQUEST, new MessageResponse(emptyMessage));
        } else {
            boolean isUserExists = userRepository.existsByEmailAndPassword(email, password);
            String name = userRepository.getByUserEmailId(email).getUsername();
            if (isUserExists) {
                UserDetails userDetails = new UserDetails(email, name);
                return new ApiResponse(HttpServletResponse.SC_OK, new MessageResponse(MessageConstant.USER_EXIST), userDetails);
            } else {
                return new ApiResponse(HttpServletResponse.SC_NOT_FOUND, new MessageResponse(MessageConstant.USER_DOESNT_EXIST));
            }
        }

    }
}
