package com.shop.backend.entity;

import com.shop.backend.enums.StaffRole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "staff")
@Data
public class StaffEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String shift; // Ca làm việc (Sáng/Chiều/Tối)

    @Enumerated(EnumType.STRING)
    private StaffRole role;

    @ManyToOne
    @JoinColumn(name = "parking_lot_id")
    private ParkingLotEntity parkingLot;
}