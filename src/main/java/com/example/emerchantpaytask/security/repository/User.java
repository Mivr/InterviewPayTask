package com.example.emerchantpaytask.security.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public record User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id,

    @Column(unique = true, nullable = false)
    String username,

    @Column(nullable = false)
    String password
) {
}