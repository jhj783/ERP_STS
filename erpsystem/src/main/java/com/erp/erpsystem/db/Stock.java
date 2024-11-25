package com.erp.erpsystem.db;

import java.math.BigDecimal;

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
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name; // 재고 이름

    @Column(nullable = false)
    private BigDecimal price; // 가격

    @Column(nullable = false)
    private int quantity; // 갯수
    
    @Column(length = 100)
    private String category; // 카테고리
    
    private int reorderLevel; // 재주문 수준

}
