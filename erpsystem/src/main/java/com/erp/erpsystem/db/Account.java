package com.erp.erpsystem.db;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Id

    @Column(nullable = false)
    private Double amount; // 거래 금액

    @Column(nullable = false)
    private LocalDateTime date; // 거래 날짜

    @Column(length = 500)
    private String description; // 거래 설명

    @Column(length = 100)
    private String type; // 거래 유형

    @Column(nullable = false)
    private Double afterBalance; // 거래 후 잔액
}
