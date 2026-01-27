package com.shop.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "parking_lots") // Khớp bảng 'parking_lots'
@Data
public class ParkingLotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Ví dụ: Vincom Đồng Khởi

    private String address;

    private Double lat;
    private Double lng;

    @Column(name = "total_capacity")
    private Integer totalCapacity;

    @Column(name = "image_url")
    private String imageUrl;
}