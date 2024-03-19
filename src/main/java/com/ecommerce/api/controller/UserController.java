package com.ecommerce.api.controller;


import com.ecommerce.api.dto.user.UserDTO;
import com.ecommerce.api.model.User;
import com.ecommerce.api.service.UserService;
import com.ecommerce.api.util.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("")
    public User getByUserEmailId(@RequestBody UserDTO userDTO){
        return userService.getByUserEmailId(userDTO);
    }


    @PostMapping("/login")
    public ApiResponse login(@RequestBody UserDTO user) {
        return userService.login(user);
    }
}
