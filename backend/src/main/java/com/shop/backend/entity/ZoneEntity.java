package com.shop.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "zones")
@Data
public class ZoneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; 

    @ManyToOne
    @JoinColumn(name = "parking_lot_id")
    private ParkingLotEntity parkingLot;
}