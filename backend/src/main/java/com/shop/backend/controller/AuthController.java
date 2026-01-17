package com.shop.backend.controller;

import com.shop.backend.dto.UserRegisterDTO;
import com.shop.backend.entity.User;
import com.shop.backend.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth") 
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;


    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register") 
    public ResponseEntity<?> register(@RequestBody UserRegisterDTO request) {

        try {

            User newUser = userService.registerUser(request);
            return ResponseEntity
                    .status(HttpStatus.CREATED) 
                    .body(newUser);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.CONFLICT) 
                    .body(e.getMessage());
            
        }
    }
}