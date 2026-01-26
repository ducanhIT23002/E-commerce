package com.shop.backend.service;

import com.shop.backend.dto.ApiResponseDTO; // Import class này
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

    // Sửa kiểu trả về thành ApiResponseDTO
    public ApiResponseDTO<JwtResponse> login(LoginRequest request) {
        
        // 1. Xác thực (Nếu sai pass, Spring sẽ tự ném BadCredentialsException ra ngoài)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 2. Tạo Token
        String jwt = jwtUtils.generateToken(request.getEmail());

        // 3. Lấy thông tin User
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Error: Email not found!"));

        // 4. Tạo Object Data
        JwtResponse jwtResponse = new JwtResponse(jwt, user.getId(), user.getUsername(), user.getEmail());

        // 5. Đóng gói vào hộp chuẩn và trả về
        return new ApiResponseDTO<>(200, "Login successful", jwtResponse);
    }
}