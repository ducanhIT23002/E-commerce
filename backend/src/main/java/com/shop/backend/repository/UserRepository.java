package com.shop.backend.repository;

import com.shop.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Tìm user bằng username
    Optional<User> findByUsername(String username);
    
    // Tìm user bằng email
    Optional<User> findByEmail(String email);

    // Kiểm tra xem username đã tồn tại chưa (Trả về True/False)
    Boolean existsByUsername(String username);

    // Kiểm tra xem email đã tồn tại chưa
    Boolean existsByEmail(String email);
}