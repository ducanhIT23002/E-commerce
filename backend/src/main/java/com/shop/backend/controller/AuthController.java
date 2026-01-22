package com.shop.backend.controller;

import com.shop.backend.dto.JwtResponse;
import com.shop.backend.dto.LoginRequest;
import com.shop.backend.dto.UserRegisterDTO;
import com.shop.backend.entity.User;
import com.shop.backend.service.AuthService;
import com.shop.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    
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

    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            JwtResponse response = authService.login(request);
            
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);

        } catch (org.springframework.security.authentication.BadCredentialsException e) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED) 
                    .body(e.getMessage());          

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }
}