package com.shop.backend.entity;

import com.shop.backend.enums.SlotStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "parking_slots") 
@Data 
public class ParkingSlotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; 

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SlotStatus status = SlotStatus.AVAILABLE; 

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "zone_id")
    private ZoneEntity zone;
}