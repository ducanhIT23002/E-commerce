package com.shop.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // Nhớ import cái này
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shop.backend.dto.ApiResponseDTO;
import com.shop.backend.dto.PaymentRequestDTO;
import com.shop.backend.entity.PaymentEntity;
import com.shop.backend.service.PaymentService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/payments")
public class PaymentController {
    
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<ApiResponseDTO<PaymentEntity>> processPayment(@RequestBody PaymentRequestDTO request) {
        try {
            
            ApiResponseDTO<PaymentEntity> response = paymentService.processPayment(request.getBookingId());
            
            return ResponseEntity
                    .status(response.getStatus())
                    .body(response);

        } catch (Exception e) {
            
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO<>(500, e.getMessage(), null));
        }
    }
}
