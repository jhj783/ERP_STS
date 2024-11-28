package com.erp.erpsystem.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.erpsystem.db.AccountRepository;
import com.erp.erpsystem.db.Asset;
import com.erp.erpsystem.db.AssetLiabilityLog;
import com.erp.erpsystem.db.AssetLiabilityLogRepository;
import com.erp.erpsystem.db.AssetRepository;

import jakarta.transaction.Transactional;

@Service
public class RefreshService {

	@Autowired
	private AccountRepository accountRepository;
	
    @Autowired
    private AssetRepository assetRepository;
    
    @Autowired
    private AssetLiabilityLogRepository assetLiabilityLogRepository;
	
    // 자산 "예치금" 업데이트
	@Transactional
	public void updateAssetByAccount() {
		Asset asset = assetRepository.findByName("예치금");
		if (asset != null) {
            asset.setAmount(accountRepository.findAfterBalanceToMaxTuno());
        }
	}
	
	// 자산 추가
	@Transactional
	public void insertAssetByAsset(String name, BigDecimal amount, BigDecimal acquisitionCost, String type) {
		Asset newAsset = new Asset();
		 LocalDateTime now = LocalDateTime.now();
		
		newAsset.setName(name);
        newAsset.setAmount(amount);
        newAsset.setAcquisitionCost(acquisitionCost);
        newAsset.setType(type);
        newAsset.setDate(now);
        assetRepository.save(newAsset);
        
        // 로그
        insertLogByAsset(name, amount, "new 자산", now);
	}

	// 자산 삭제
	@Transactional
	public void deleteAssetByName(String name) {
	    Asset assetToDelete = assetRepository.findByName(name);
	    
	    if (assetToDelete != null) {
	        BigDecimal amount = assetToDelete.getAmount().negate();
	        LocalDateTime date = LocalDateTime.now(); // 삭제 시점 기록

	        assetRepository.delete(assetToDelete);

	        // 로그
	        insertLogByAsset(name, amount, "deleted 자산", date);
	    } else {
	        throw new IllegalArgumentException("No asset found with name: " + name);
	    }
	}
	
	// 로그 추가
	@Transactional
	public void insertLogByAsset(String name, BigDecimal amount, String description, LocalDateTime date) {
		AssetLiabilityLog newAssetLiabilityLog = new AssetLiabilityLog();
		newAssetLiabilityLog.setName(name);
		newAssetLiabilityLog.setAmount(amount);  
		newAssetLiabilityLog.setDescription(description);
		newAssetLiabilityLog.setDate(date);
		assetLiabilityLogRepository.save(newAssetLiabilityLog);
	}
	
	
	//
}
