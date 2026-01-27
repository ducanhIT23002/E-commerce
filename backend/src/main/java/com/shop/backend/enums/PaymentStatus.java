package com.shop.backend.enums;

public enum PaymentStatus {
    UNPAID,  // Chưa thanh toán
    PAID,    // Đã thanh toán thành công
    FAILED,  // Thanh toán thất bại (ví dụ: không đủ tiền)
    REFUNDED // Đã hoàn tiền (dành cho trường hợp hủy)
}