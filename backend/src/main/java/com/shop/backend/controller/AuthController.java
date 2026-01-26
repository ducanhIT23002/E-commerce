package com.shop.backend.controller;

import com.shop.backend.dto.ApiResponseDTO; // Import class này
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
    public ResponseEntity<ApiResponseDTO<User>> register(@RequestBody UserRegisterDTO request) {
        try {
            
            ApiResponseDTO<User> response = userService.registerUser(request);
            
            return ResponseEntity
                    .status(response.getStatus()) 
                    .body(response);

        } catch (RuntimeException e) {
            
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new ApiResponseDTO<>(409, e.getMessage(), null));
                    
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO<>(500, "Registration failed: " + e.getMessage(), null));
        }
    }

    
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<JwtResponse>> login(@RequestBody LoginRequest request) {
        try {
            
            ApiResponseDTO<JwtResponse> response = authService.login(request);
            
            return ResponseEntity
                    .status(response.getStatus()) 
                    .body(response);

        } catch (org.springframework.security.authentication.BadCredentialsException e) {
           
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponseDTO<>(401, "Invalid email or password", null));

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO<>(500, "Internal Server Error: " + e.getMessage(), null));
        }
    }
}