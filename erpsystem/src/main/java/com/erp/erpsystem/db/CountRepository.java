package com.erp.erpsystem.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;

public interface CountRepository extends JpaRepository<Count, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Count c SET c.istuno = c.istuno + 1 WHERE c.id = 1")
    void incrementIstuno();
}
