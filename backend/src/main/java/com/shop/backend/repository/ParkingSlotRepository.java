package com.shop.backend.repository;

import com.shop.backend.entity.ParkingSlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository 
public interface ParkingSlotRepository extends JpaRepository<ParkingSlotEntity, Long> { 

    
    boolean existsByName(String name);

    
    Optional<ParkingSlotEntity> findByName(String name);
}