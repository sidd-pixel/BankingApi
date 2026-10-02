package com.example.banking_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.banking_api.dto.RegisterRequest;
import com.example.banking_api.dto.UserResponse;
import com.example.banking_api.model.User;
import com.example.banking_api.service.UserService;

@RestController 
@RequestMapping ("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService){
        this.userService=userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse>register(@RequestBody RegisterRequest request){
        User createdUser=userService.createUser(request);

        UserResponse response=new UserResponse(createdUser.getId(), createdUser.getUsername(), createdUser.getRole());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
