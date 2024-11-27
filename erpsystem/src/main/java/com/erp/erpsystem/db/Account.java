package com.erp.erpsystem.db;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
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
    private BigDecimal amount; // 거래 금액

    @Column(nullable = false)
    private LocalDateTime date; // 거래 날짜

    @Column(length = 500)
    private String description; // 거래 설명

    @Column(length = 100)
    private String type; // 거래 유형

    @Column(nullable = false)
    private BigDecimal afterBalance; // 거래 후 잔액
    
    @Column(nullable = true)
    private Integer tuno; //  거래고유번호
    
    @PrePersist
    public void prePersist() {
        if (this.tuno == null) {
            this.tuno = 0; // 기본값 설정
        }
    }
}
