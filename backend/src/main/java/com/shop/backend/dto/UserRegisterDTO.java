package com.shop.backend.dto;

import lombok.Data;

@Data 
public class UserRegisterDTO {
    private String email;
    private String password;
    private String fullName;
    private String phone;
}