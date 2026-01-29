package com.shop.backend.controller;

import com.shop.backend.dto.ApiResponseDTO;
import com.shop.backend.entity.ParkingSlotEntity;
import com.shop.backend.enums.SlotStatus;
import com.shop.backend.service.ParkingSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking-slots")
@CrossOrigin(origins = "*") 
public class ParkingSlotController {

    @Autowired
    private ParkingSlotService parkingSlotService;

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<ParkingSlotEntity>>> getAllSlots() {
        try {
            ApiResponseDTO<List<ParkingSlotEntity>> response = parkingSlotService.getAllSlots();

            return ResponseEntity
                    .status(response.getStatus())
                    .body(response);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO<>(500, "Failed to retrieve slots: " + e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<ParkingSlotEntity>> createSlot(@RequestBody ParkingSlotEntity slot) {
        try {
            ApiResponseDTO<ParkingSlotEntity> response = parkingSlotService.createSlot(slot);

            return ResponseEntity
                    .status(response.getStatus())
                    .body(response);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO<>(500, "Error creating slot: " + e.getMessage(), null));
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponseDTO<ParkingSlotEntity>> updateStatus(
            @PathVariable Long id,
            @RequestParam SlotStatus status) {
        try {
            ApiResponseDTO<ParkingSlotEntity> response = parkingSlotService.updateStatus(id, status);

            return ResponseEntity
                    .status(response.getStatus())
                    .body(response);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO<>(500, "Error updating status: " + e.getMessage(), null));
        }
    }
}