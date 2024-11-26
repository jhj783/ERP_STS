package com.erp.erpsystem.db;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StockRepository extends JpaRepository<Stock, Long> {
	@Query("SELECT a.name, a.quantity FROM Stock a")
	List<Object[]> findChartStockData();
	
	Optional<Stock> findByName(String name);

}
