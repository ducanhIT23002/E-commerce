package com.shop.backend.entity;

import com.shop.backend.enums.SlotStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity // (1)
@Table(name = "parking_slots") // (2)
@Data // (3)
public class ParkingSlotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // (4)
    private Long id;

    @Column(nullable = false, unique = true) // (5)
    private String name; 

    @Enumerated(EnumType.STRING) // (6)
    @Column(nullable = false)
    private SlotStatus status = SlotStatus.AVAILABLE; // (7)
}