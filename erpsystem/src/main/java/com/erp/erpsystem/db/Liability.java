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
public class Liability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 부채의 고유 식별자

    @Column(nullable = false, length = 200)
    private String name; // 부채 이름

    @Column(nullable = false)
    private BigDecimal currentValue; // 부채의 현재 가치

    @Column(length = 100)
    private String type; // 부채 유형 (유동/비유동 등)

    @Column(nullable = false)
    private BigDecimal originValue; // 부채의 원래 가치

    @Column(nullable = false)
    private LocalDateTime date; // 부채 발생 날짜

    @Column(nullable = false)
    private BigDecimal interestRate; // 이자율

    @Column(nullable = false, length = 100)
    private String interestPeriod; // 이자 주기

    @Column(nullable = false)
    private LocalDateTime maturityDate; // 만기일
}
