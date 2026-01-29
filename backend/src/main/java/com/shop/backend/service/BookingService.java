package com.shop.backend.service;

import com.shop.backend.dto.ApiResponseDTO;
import com.shop.backend.dto.BookingDTO;
import com.shop.backend.entity.BookingEntity;
import com.shop.backend.entity.ParkingSlotEntity;
import com.shop.backend.entity.User;
import com.shop.backend.entity.VehicleEntity;
import com.shop.backend.enums.BookingStatus;
import com.shop.backend.enums.SlotStatus;
import com.shop.backend.repository.BookingRepository;
import com.shop.backend.repository.ParkingSlotRepository;
import com.shop.backend.repository.UserRepository;
import com.shop.backend.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Transactional
    public ApiResponseDTO<BookingEntity> createBooking(BookingDTO bookingDTO) {

        ParkingSlotEntity slot = parkingSlotRepository.findById(bookingDTO.getSlotId())
                .orElseThrow(() -> new RuntimeException("Parking slot not found!"));

        if (!slot.getStatus().equals(SlotStatus.AVAILABLE)) {
            return new ApiResponseDTO<>(409, "Parking slot is not available", null);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();
        
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        VehicleEntity vehicle;
        
        if (bookingDTO.getVehicleId() != null) {

            vehicle = vehicleRepository.findById(bookingDTO.getVehicleId())
                    .orElseThrow(() -> new RuntimeException("Vehicle not found!"));

        } else if (bookingDTO.getLicensePlate() != null) {

            vehicle = vehicleRepository.findByPlateNumber(bookingDTO.getLicensePlate())
                    .orElse(null);
            
            if (vehicle == null) {
                vehicle = new VehicleEntity();
                vehicle.setPlateNumber(bookingDTO.getLicensePlate()); 
                vehicle.setUser(currentUser);
                vehicle = vehicleRepository.save(vehicle);
            }

        } else {
            throw new RuntimeException("Vehicle ID or License Plate is required!");
        }

        BookingEntity booking = new BookingEntity();
        booking.setUser(currentUser);
        booking.setSlot(slot);
        booking.setVehicle(vehicle);
        booking.setStartTime(bookingDTO.getStartTime());
        booking.setEndTime(bookingDTO.getEndTime());
        booking.setStatus(BookingStatus.PENDING);
        
        booking.setTotalFee(calculateFee(bookingDTO.getStartTime(), bookingDTO.getEndTime()));

        BookingEntity savedBooking = bookingRepository.save(booking);

        slot.setStatus(SlotStatus.BOOKED);
        parkingSlotRepository.save(slot);

        return new ApiResponseDTO<>(201, "Booking created successfully", savedBooking);
    }

    private java.math.BigDecimal calculateFee(java.time.LocalDateTime startTime, java.time.LocalDateTime endTime) {

        if (startTime == null || endTime == null) return java.math.BigDecimal.ZERO;

        long durationMinutes = java.time.temporal.ChronoUnit.MINUTES.between(startTime, endTime);

        long durationHours = (durationMinutes + 59) / 60; 

        return new java.math.BigDecimal(durationHours * 50000);

    }

    public ApiResponseDTO<List<BookingEntity>> getMyBookings() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();
        
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        
        List<BookingEntity> bookings = bookingRepository.findByUser(currentUser);

        return new ApiResponseDTO<>(200, "Retrieved user bookings", bookings);
    }

}