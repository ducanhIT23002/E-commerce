package com.shop.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email; 
    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "full_name")
    private String fullName;

    private String phone;

    private BigDecimal balance = BigDecimal.ZERO;
}