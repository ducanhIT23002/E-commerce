package com.shop.backend.dto;

import lombok.Data;

@Data // Tự động sinh Getter/Setter
public class UserRegisterDTO {
    private String username;
    private String password;
    private String email;
}