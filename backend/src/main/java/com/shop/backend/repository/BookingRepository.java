package com.shop.backend.repository;

import com.shop.backend.entity.BookingEntity;
import com.shop.backend.entity.ParkingSlotEntity;
import com.shop.backend.entity.User;
import com.shop.backend.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    List<BookingEntity> findByUser(User user);
    List<BookingEntity> findBySlot(ParkingSlotEntity slot);
    List<BookingEntity> findBySlotAndStatus(ParkingSlotEntity slot, BookingStatus status);
    List<BookingEntity> findByStartTimeBeforeAndEndTimeAfter(LocalDateTime endTime, LocalDateTime startTime);

    // Optional<User> findByIdAndUserId(Long bookingId, Long id);
}