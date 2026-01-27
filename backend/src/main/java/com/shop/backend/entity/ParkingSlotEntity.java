package com.shop.backend.entity;

import com.shop.backend.enums.SlotStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "parking_slots") // Khớp bảng 'parking_slots'
@Data // Lombok sẽ tự sinh getStatus, setStatus
public class ParkingSlotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Ví dụ: A-01

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SlotStatus status = SlotStatus.AVAILABLE; // Phải có dòng này thì mới có setStatus

    @Column(name = "image_url")
    private String imageUrl;

    // Quan hệ với Zone
    @ManyToOne
    @JoinColumn(name = "zone_id")
    private ZoneEntity zone;
}