package com.shop.backend.repository;

import com.shop.backend.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {
    
    Optional<VehicleEntity> findByPlateNumber(String plateNumber);
    
    // List<VehicleEntity> findByUserId(Long userId);
}