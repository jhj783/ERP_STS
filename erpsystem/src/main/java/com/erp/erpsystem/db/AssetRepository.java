package com.erp.erpsystem.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AssetRepository extends JpaRepository<Asset, Long> {
   
    Asset findByName(String name);
    
    @Query("SELECT a FROM Asset a") 
    List<Asset> findAssetData();
    
    @Query("SELECT a.name, a.amount FROM Asset a")
    List<Object[]> findAssetRatioData();
}
