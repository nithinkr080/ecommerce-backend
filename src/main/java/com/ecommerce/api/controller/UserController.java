package com.ecommerce.api.controller;


import com.ecommerce.api.dto.user.UserDTO;
import com.ecommerce.api.model.User;
import com.ecommerce.api.service.AuthenticateService;
import com.ecommerce.api.util.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@CrossOrigin
public class UserController {

    @Autowired
   final private AuthenticateService authenticateService;

    public UserController(AuthenticateService authenticateService) {
        this.authenticateService = authenticateService;
    }

    @PostMapping("")
    public User getByUserEmailId(@RequestBody UserDTO userDTO) {
        return authenticateService.getByUserEmailId(userDTO);
    }



    @PostMapping("/signIn")
    public ApiResponse signIn(@RequestBody UserDTO user) {
        return authenticateService.signIn(user);
    }

    @PostMapping("/signUp")
    public ApiResponse signUp(@RequestBody UserDTO user) {
        return authenticateService.signUp(user);
    }
}
