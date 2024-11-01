package com.erp.erpsystem.db;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Long> {
	Asset findByName(String name);
}