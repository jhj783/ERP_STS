package com.erp.erpsystem.db;

import java.math.BigDecimal;
import java.time.LocalDate;

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
public class Equity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 자본의 고유 식별자

    @Column(nullable = false, length = 200)
    private String name; // 자본 이름

    @Column(nullable = false)
    private BigDecimal amount; // 자본 금액

    @Column(length = 100)
    private String type; // 자본 유형 (예: 유상 자본, 기타 자본 등)

    @Column(nullable = false)
    private LocalDate date; // 자본 변동 날짜

    @Column(length = 500)
    private String reason; // 변동 사유
}
