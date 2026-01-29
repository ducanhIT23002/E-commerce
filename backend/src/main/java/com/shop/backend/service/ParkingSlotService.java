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

    public ApiResponseDTO<List<ParkingSlotEntity>> getAllSlots() {
        List<ParkingSlotEntity> slots = parkingSlotRepository.findAll();
        return new ApiResponseDTO<>(200, "get list successfully", slots);
    }

    public ApiResponseDTO<ParkingSlotEntity> createSlot(ParkingSlotEntity slot) {
        if (parkingSlotRepository.existsByName(slot.getName())) {
            return new ApiResponseDTO<>(409, "name already exists!", null);
        }

        slot.setStatus(SlotStatus.AVAILABLE);
        
        ParkingSlotEntity savedSlot = parkingSlotRepository.save(slot);
        
        return new ApiResponseDTO<>(201, "create successfully", savedSlot);
    }

    public ApiResponseDTO<ParkingSlotEntity> updateStatus(Long id, SlotStatus newStatus) {
        ParkingSlotEntity slot = parkingSlotRepository.findById(id).orElse(null);

        if (slot == null) {
            return new ApiResponseDTO<>(404, "Slot not found", null);
        }

        slot.setStatus(newStatus);
        ParkingSlotEntity updatedSlot = parkingSlotRepository.save(slot);

        return new ApiResponseDTO<>(200, "update status successfully", updatedSlot);
    }
}