package com.shop.backend.service;

import com.shop.backend.dto.UserRegisterDTO;
import com.shop.backend.entity.User;
import com.shop.backend.repository.UserRepository;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils; 
import org.springframework.security.crypto.password.PasswordEncoder; 



@Service 
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

   
    public User registerUser(UserRegisterDTO request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists!");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists!");
        }

        User newUser = new User();

        BeanUtils.copyProperties(request, newUser);

        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        newUser.setRole("USER"); 

 
        return userRepository.save(newUser);
    }
}