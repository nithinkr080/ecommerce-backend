package com.ecommerce.api.service.impl;

import com.ecommerce.api.constants.MessageConstant;
import com.ecommerce.api.dto.user.UserDTO;
import com.ecommerce.api.model.User;
import com.ecommerce.api.respository.UserRepository;
import com.ecommerce.api.service.AuthenticateService;
import com.ecommerce.api.util.response.ApiResponse;
import com.ecommerce.api.util.response.MessageResponse;
import lombok.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
public class AuthenticateServiceImpl implements AuthenticateService {
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
        private String role;
        private Optional<Long> userId;
    }

    @Override
    public ApiResponse signIn(UserDTO userDTO) {
        String email = userDTO.getEmailId();
        String password = userDTO.getPassword();
        if ((email == null || password == null || email.isEmpty()) || password.isEmpty()) {
            String emptyMessage = email == null || email.isEmpty() ? MessageConstant.EMPTY_EMAIL : MessageConstant.EMPTY_PASSWORD;
            return new ApiResponse(HttpServletResponse.SC_BAD_REQUEST, new MessageResponse(emptyMessage));
        } else {
            boolean isUserExists = userRepository.existsByEmailAndPassword(email, password);
            String name = userRepository.getByUserEmailId(email).getUsername();
            String role = userRepository.getByUserEmailId(email).getRole();
            Long userId = userRepository.getByUserEmailId(email).getUserId();
            if (isUserExists) {
                UserDetails userDetails = new UserDetails(email, name, role, Optional.ofNullable(userId));
                return new ApiResponse(HttpServletResponse.SC_OK, new MessageResponse(MessageConstant.SIGN_IN), userDetails);
            } else {
                return new ApiResponse(HttpServletResponse.SC_NOT_FOUND, new MessageResponse(MessageConstant.USER_DOESNT_EXIST));
            }
        }

    }

    @Override
    public ApiResponse signUp(@NonNull UserDTO userDTO) {
        String emailId = userDTO.getEmailId();
        String password = userDTO.getPassword();
        String username = userDTO.getUsername();
        String role = userDTO.getRole();
        boolean isEmailExists = userRepository.existsByEmail(emailId);
        UserDetails userDetails = new UserDetails(emailId, username, role, Optional.empty());
        if (isEmailExists) {
            return new ApiResponse(HttpServletResponse.SC_FORBIDDEN, new MessageResponse(MessageConstant.USER_ALREADY_EXISTS), userDetails);
        } else {
            int isInserted = userRepository.insertUser(emailId, username, password, role);
            if (isInserted == 1) {
                return new ApiResponse(HttpServletResponse.SC_OK, new MessageResponse(MessageConstant.SIGN_UP), userDetails);
            } else {
                return new ApiResponse(HttpServletResponse.SC_FORBIDDEN, new MessageResponse(MessageConstant.SOMETHING_WENT_WRONG));
            }
        }
    }
}
