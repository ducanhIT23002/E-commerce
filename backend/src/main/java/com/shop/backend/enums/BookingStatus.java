package com.shop.backend.enums;

public enum BookingStatus {
    PENDING,    // Vừa bấm đặt, chưa thanh toán (nếu có logic giữ chỗ)
    CONFIRMED,  // Đã đặt thành công
    COMPLETED,  // Đã hoàn thành (xe đã rời bãi)
    CANCELLED   // Đã hủy
}