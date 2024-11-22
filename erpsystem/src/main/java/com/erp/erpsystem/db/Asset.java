package com.erp.erpsystem.db;

import java.math.BigDecimal;
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
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 자산의 고유 식별자

    @Column(nullable = false, length = 200)
    private String name; // 자산 이름

    @Column(nullable = false)
    private BigDecimal amount; // 자산의 현재 가치

    @Column(length = 100)
    private String type; // 자산 유형 (유동/비유동 등)

    @Column(nullable = false)
    private BigDecimal acquisitionCost; // 자산의 취득가액
    
    @Column(nullable = false)
    private LocalDateTime date; // 취득일
}
