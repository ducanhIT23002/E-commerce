package com.shop.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
public class TestController {
    // Cho phép mọi nơi gọi vào
    @CrossOrigin(origins = "*") 
    @GetMapping("/test")
    public String testConnection() {
        return "HELLO TỪ BACKEND! Chúng ta đã kết nối thành công!";
    }
}