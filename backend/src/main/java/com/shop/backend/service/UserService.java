package com.shop.backend.service;

import com.shop.backend.dto.ApiResponseDTO;
import com.shop.backend.dto.UserRegisterDTO;
import com.shop.backend.entity.User;
import com.shop.backend.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ApiResponseDTO<User> registerUser(UserRegisterDTO request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return new ApiResponseDTO<>(409, "Email already exists!", null);
        }

        User newUser = new User();
        
        BeanUtils.copyProperties(request, newUser);
        
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        
        newUser.setBalance(BigDecimal.ZERO);

        User savedUser = userRepository.save(newUser);

        return new ApiResponseDTO<>(201, "Registration successful!", savedUser);
    }
}