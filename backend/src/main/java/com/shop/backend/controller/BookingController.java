package com.shop.backend.controller;

import com.shop.backend.dto.ApiResponseDTO;
import com.shop.backend.dto.BookingDTO;
import com.shop.backend.entity.BookingEntity;
import com.shop.backend.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<BookingEntity>> createBooking(@RequestBody BookingDTO bookingDTO) {
        try {
            ApiResponseDTO<BookingEntity> response = bookingService.createBooking(bookingDTO);

            return ResponseEntity
                    .status(response.getStatus())
                    .body(response);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO<>(500, "Error: " + e.getMessage(), null));
        }
    }

    @GetMapping("/my-bookings")
    public ResponseEntity<ApiResponseDTO<List<BookingEntity>>> getMyBookings() {
        try {
            ApiResponseDTO<List<BookingEntity>> response = bookingService.getMyBookings();

            return ResponseEntity
                    .status(response.getStatus())
                    .body(response);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO<>(500, "Error: " + e.getMessage(), null));
        }
    }
}