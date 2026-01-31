package com.shop.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.shop.backend.dto.ApiResponseDTO;
import com.shop.backend.entity.BookingEntity;
import com.shop.backend.entity.PaymentEntity;
import com.shop.backend.enums.BookingStatus;
import com.shop.backend.enums.PaymentMethod;
import com.shop.backend.repository.BookingRepository;
import com.shop.backend.repository.PaymentRepository;
import com.shop.backend.repository.UserRepository;
import com.shop.backend.entity.User;

import jakarta.transaction.Transactional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Transactional
    public ApiResponseDTO<PaymentEntity> processPayment(Long bookingId) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User CurrentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        BookingEntity booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found for this user"));
        
        if(booking == null) {
            return new ApiResponseDTO<>(404, "Booking not found", null);
        }

        if (!booking.getUser().getId().equals(CurrentUser.getId())) {
            return new ApiResponseDTO<>(403, "You are not authorized to pay for this booking", null);
        }
        if (booking.getStatus() != BookingStatus.PENDING) { 
            return new ApiResponseDTO<>(400, "Booking is not in a payable state", null);
        }

        if (CurrentUser.getBalance().compareTo(booking.getTotalFee()) < 0) {
            return new ApiResponseDTO<>(400, "Insufficient balance", null);
        }

        CurrentUser.setBalance(CurrentUser.getBalance().subtract(booking.getTotalFee()));

        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);

        PaymentEntity savedPayment = new PaymentEntity();
        savedPayment.setBooking(booking);
        savedPayment.setAmount(booking.getTotalFee());
        savedPayment.setMethod(PaymentMethod.WALLET);

        paymentRepository.save(savedPayment);

        return new ApiResponseDTO<>(200, "Payment processed successfully",savedPayment );
    }
}
