package com.shop.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat; // 👈 QUAN TRỌNG: Phải import cái này
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingDTO {
    private Long slotId;
    private Long vehicleId;
    private String licensePlate;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private LocalDateTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private LocalDateTime endTime;
}