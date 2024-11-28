package com.erp.erpsystem.db;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StockRepository extends JpaRepository<Stock, Long> {
	@Query("SELECT a.name, a.quantity FROM Stock a")
	List<Object[]> findChartStockData();

	Optional<Stock> findByName(String name);

	// 재고자산
	@Query("SELECT SUM(s.price * s.quantity) FROM Stock s")
	BigDecimal findStockAsset();

	@Query("SELECT s.name FROM Stock s")
	List<String> findAllNames();

}

