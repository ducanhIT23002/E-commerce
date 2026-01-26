package com.shop.backend.service;

import com.shop.backend.dto.ApiResponseDTO;
import com.shop.backend.entity.ParkingSlotEntity;
import com.shop.backend.enums.SlotStatus;
import com.shop.backend.repository.ParkingSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingSlotService {

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    // 1. Get all slots
    public ApiResponseDTO<List<ParkingSlotEntity>> getAllSlots() {
        List<ParkingSlotEntity> slots = parkingSlotRepository.findAll();
        
        return new ApiResponseDTO<>(200, "Retrieved all parking slots successfully", slots);
    }

    // 2. Create slot
    public ApiResponseDTO<ParkingSlotEntity> createSlot(ParkingSlotEntity slot) {
        if (parkingSlotRepository.existsByName(slot.getName())) {
            throw new RuntimeException("Parking slot with this name already exists!");
        }
        
        slot.setStatus(SlotStatus.AVAILABLE);
        
        ParkingSlotEntity savedSlot = parkingSlotRepository.save(slot);
        
        return new ApiResponseDTO<>(201, "Parking slot created successfully", savedSlot);
    }

    public ApiResponseDTO<ParkingSlotEntity> updateStatus(Long id, SlotStatus newStatus) {
        ParkingSlotEntity slot = parkingSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parking slot not found!"));
        
        slot.setStatus(newStatus);
        
        ParkingSlotEntity updatedSlot = parkingSlotRepository.save(slot);
        
        return new ApiResponseDTO<>(200, "Slot status updated successfully", updatedSlot);
    }
}