package com.shop.backend.service;

import com.shop.backend.dto.ApiResponseDTO;
import com.shop.backend.dto.JwtResponse;
import com.shop.backend.dto.LoginRequest;
import com.shop.backend.entity.User;
import com.shop.backend.repository.UserRepository;
import com.shop.backend.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager; 

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils; 

    public ApiResponseDTO<JwtResponse> login(LoginRequest request) {
        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtils.generateToken(request.getEmail());

            User user = userRepository.findByEmail(request.getEmail()).orElse(null);
            
            if (user == null) {
                return new ApiResponseDTO<>(404, "User not found!", null);
            }

            JwtResponse jwtResponse = new JwtResponse(
                    jwt, 
                    user.getId(), 
                    user.getEmail(), 
                    user.getFullName()
            );

            return new ApiResponseDTO<>(200, "Login successful", jwtResponse);

        } catch (Exception e) {
            return new ApiResponseDTO<>(401, "Email or password is incorrect!", null);
        }
    }
}