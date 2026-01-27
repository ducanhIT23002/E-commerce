package com.shop.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    private Long slotId;
    private Long vehicleId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String licensePlate;  // Alternative if vehicle not pre-selected
}