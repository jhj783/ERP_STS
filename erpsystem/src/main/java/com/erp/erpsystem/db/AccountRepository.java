package com.erp.erpsystem.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    // 제일 마지막 ID의 AFTER_BALANCE 값
    Account findTopByOrderByIdDesc();
    
    // 특정 TYPE과 날짜 범위의 AMOUNT 합계
    @Query("SELECT SUM(a.amount) FROM Account a WHERE a.type = :type AND a.date BETWEEN :startDate AND :endDate")
    BigDecimal findTotalAmountByTypeAndDateRange(
        @Param("type") String type,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    // 특정 DESCRIPTION과 날짜 범위의 AMOUNT 합계
    @Query("SELECT SUM(a.amount) FROM Account a WHERE a.description = :description AND a.date BETWEEN :startDate AND :endDate")
    BigDecimal findTotalAmountByDescriptionAndDateRange(
        @Param("description") String description,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    // 특정 조건의 DESCRIPTION들로 시작하거나 포함된 경우의 AMOUNT 합계
    @Query("SELECT SUM(a.amount) FROM Account a WHERE (a.description LIKE CONCAT(:descriptionPrefix, '%') OR a.description IN :descriptions) AND a.date BETWEEN :startDate AND :endDate")
    BigDecimal findTotalAmountByDescriptionCriteria(
        @Param("descriptionPrefix") String descriptionPrefix,
        @Param("descriptions") List<String> descriptions,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
    
    // ERP_ChartsService.운영비용 분포
    @Query(value = "SELECT description, SUM(amount) FROM account WHERE description IN :descriptions AND date BETWEEN :startDate AND :endDate GROUP BY description", nativeQuery = true)
    List<Object[]> findAccountSummaries(
        @Param("descriptions") List<String> descriptions,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    // Tuno의 가장 높은 값
    @Query("SELECT MAX(a.tuno) FROM Account a")
    int findMaxTuno();
    
    // Tuno가 가장 높은 afterbalance
    @Query("SELECT a.afterBalance FROM Account a WHERE a.tuno = (SELECT MAX(tuno) FROM Account)")
    BigDecimal findAfterBalanceToMaxTuno();

    
}
