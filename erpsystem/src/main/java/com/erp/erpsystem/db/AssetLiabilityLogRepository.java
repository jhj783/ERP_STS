package com.erp.erpsystem.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AssetLiabilityLogRepository extends JpaRepository<AssetLiabilityLog, Long> {

	// 감가상각
    @Query("SELECT SUM(a.amount) FROM AssetLiabilityLog a WHERE a.description = :description AND a.date BETWEEN :startDate AND :endDate")
    Double findNameAndAmountByDescriptionAndDateRange(
        @Param("description") String description,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
    
    @Query("SELECT SUM(a.amount) FROM AssetLiabilityLog a WHERE a.description = :description AND a.date BETWEEN :startDate AND :endDate")
    Double findTotalAmountByDescriptionAndDateRange(
        @Param("description") String description,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
    
    @Query("SELECT SUM(a.amount) FROM AssetLiabilityLog a WHERE a.name = :name AND a.date <= :endDate")
    Double findTotalAmountByCashDateRange(
        @Param("name") String description, // name으로 바꿔야?
        @Param("endDate") LocalDateTime endDate
    );
    
    // 비유동자산
    @Query("SELECT a.name, a.amount FROM AssetLiabilityLog a WHERE a.description = :description AND a.name IN :names AND a.date <= :endDate")
    List<Object[]> findAmountsByDescriptionAndNames(
        @Param("description") String description,
        @Param("names") List<String> names,
        @Param("endDate") LocalDateTime endDate
    );

    
    @Query("SELECT SUM(a.amount) FROM AssetLiabilityLog a WHERE a.description = :description AND a.date <= :endDate")
    Double findTotalAmountByWriteDownDateRange(
		@Param("description") String description,
        @Param("endDate") LocalDateTime endDate
    );
    
    @Query("SELECT SUM(a.amount) FROM AssetLiabilityLog a WHERE a.name IN :shortTermDebtNames AND a.date <= :endDate")
    Double findShortTermDebt(
        @Param("shortTermDebtNames") List<String> shortTermDebtNames,
        @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT SUM(a.amount) FROM AssetLiabilityLog a WHERE a.name IN :equityNames AND a.date <= :endDate")
    Double findEquity(
        @Param("equityNames") List<String> equityNames,
        @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT SUM(a.amount) FROM AssetLiabilityLog a WHERE a.name NOT IN :exclusionNames AND a.description = 'new 자산' AND a.date BETWEEN :startDate AND :endDate")
    Double findCashFlow(
        @Param("exclusionNames") List<String> exclusionNames,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
    
    
    @Query("SELECT SUM(a.amount) FROM AssetLiabilityLog a WHERE a.description = 'new 부채' AND a.date BETWEEN :startDate AND :endDate")
    Double findCashFlowFinancial(
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
    
}