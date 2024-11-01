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
public class AssetLiabilityLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 로그의 고유 식별자

    @Column(nullable = false, length = 200)
    private String name; // 로그 이름 (자산/부채 항목 이름)

    @Column(nullable = false)
    private Double amount; // 금액 변동

    @Column(length = 500)
    private String description; // 변동 내용

    @Column(nullable = false)
    private LocalDateTime date; // 변동 날짜
}
