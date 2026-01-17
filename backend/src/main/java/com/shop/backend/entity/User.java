package com.shop.backend.entity;

import jakarta.persistence.*; 
import lombok.Data; 
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity 
@Table(name = "users") 
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    private String role = "USER";
}