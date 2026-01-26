package com.shop.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseDTO<T> {
    private int status;      // Ví dụ: 200, 404
    private String message;  // Ví dụ: "Xử lý thành công"
    private T data;          // Dữ liệu chính (User, Token, List...)
}