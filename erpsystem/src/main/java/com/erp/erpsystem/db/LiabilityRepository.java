package com.erp.erpsystem.db;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface LiabilityRepository extends JpaRepository<Liability, Long> {
	@Query("SELECT a.name FROM Liability a WHERE a.maturityDate BETWEEN :endDate AND :futureDate")
	List<String> findNameShortDebtNames(
	    @Param("endDate") LocalDateTime endDate,
	    @Param("futureDate") LocalDateTime futureDate
	);
	
	@Query("SELECT a.name FROM Liability a WHERE a.maturityDate > :futureDate")
	List<String> findNameLongDebtNames(
		@Param("futureDate") LocalDateTime futureDate
	);

}
