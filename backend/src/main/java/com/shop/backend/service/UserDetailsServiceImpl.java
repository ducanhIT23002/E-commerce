package com.shop.backend.service;

import com.shop.backend.entity.User;
import com.shop.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Tìm user theo Email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Trả về User của Spring Security (Dùng email làm username)
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), // Sửa chỗ này: Lấy Email làm username
                user.getPassword(),
                new ArrayList<>()
        );
    }
}