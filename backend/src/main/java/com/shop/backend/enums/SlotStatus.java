package com.shop.backend.enums;

public enum SlotStatus {
    AVAILABLE,   // Chỗ đang trống, khách có thể đặt
    BOOKED,      // Đã có người đặt (hoặc xe đang đỗ)
    MAINTENANCE  // Đang bảo trì/sửa chữa (Admin khóa lại, không cho đặt)
}
