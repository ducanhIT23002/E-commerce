package com.shop.backend.service;

import com.shop.backend.dto.ApiResponseDTO;
import com.shop.backend.dto.UserRegisterDTO;
import com.shop.backend.entity.User;
import com.shop.backend.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ApiResponseDTO<User> registerUser(UserRegisterDTO request) {

        // Chỉ check Email trùng (Bỏ check Username)
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists!");
        }

        User newUser = new User();
        // Copy các trường khớp tên (email, password, fullName...)
        BeanUtils.copyProperties(request, newUser);
        
        // Mã hóa pass
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        
        // Set mặc định ví tiền = 0
        newUser.setBalance(BigDecimal.ZERO);

        User savedUser = userRepository.save(newUser);

        return new ApiResponseDTO<>(201, "User registered successfully", savedUser);
    }
}