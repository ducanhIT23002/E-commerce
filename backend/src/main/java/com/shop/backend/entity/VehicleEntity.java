package com.shop.backend.entity;

import com.shop.backend.enums.VehicleType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "vehicles")
@Data
public class VehicleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plate_number", nullable = false)
    private String plateNumber;

    @Enumerated(EnumType.STRING)
    private VehicleType type; // CAR hoặc MOTO

    private String brand; // Vd: Honda, Toyota

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}