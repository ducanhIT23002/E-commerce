package com.shop.backend.entity;

import com.shop.backend.enums.VehicleType;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "price_rates")
@Data
public class PriceRateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type")
    private VehicleType vehicleType;

    @Column(name = "price_per_hour")
    private BigDecimal pricePerHour; 

    @ManyToOne
    @JoinColumn(name = "zone_id")
    private ZoneEntity zone;
}