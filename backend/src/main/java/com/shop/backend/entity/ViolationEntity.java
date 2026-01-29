package com.shop.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "violations")
@Data
public class ViolationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "violation_type")
    private String violationType; 

    @Column(name = "fine_amount")
    private BigDecimal fineAmount;

    @Column(name = "image_proof_url")
    private String imageProofUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private BookingEntity booking;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private StaffEntity staff;
}