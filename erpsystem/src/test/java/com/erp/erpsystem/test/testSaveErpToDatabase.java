package com.erp.erpsystem.test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.erp.erpsystem.db.AssetLiabilityLog;
import com.erp.erpsystem.db.AssetLiabilityLogRepository;


@SpringBootTest
class testSaveErpToDatabase {
    
    @Autowired
    private AssetLiabilityLogRepository assetLiabilityLogRepository;

    @Test
    void testJpa() {
    	//자산&부채 로그 내용
        List<AssetLiabilityLog> assets_Liabilities_log = new ArrayList<>();

        assets_Liabilities_log.add(createAssetLiabilityLog("현금", 10000000.0, "new 자산", LocalDateTime.of(2023, 1, 1, 0, 0)));
        assets_Liabilities_log.add(createAssetLiabilityLog("예치금", 100000000.0, "new 자산", LocalDateTime.of(2023, 1, 1, 0, 0)));
        assets_Liabilities_log.add(createAssetLiabilityLog("토지", 30000000.0, "new 자산", LocalDateTime.of(2023, 1, 1, 0, 0)));
        assets_Liabilities_log.add(createAssetLiabilityLog("건물", 100000000.0, "new 자산", LocalDateTime.of(2023, 1, 1, 0, 0)));
        assets_Liabilities_log.add(createAssetLiabilityLog("신한은행 대출", 10000000.0, "new 부채", LocalDateTime.of(2023, 1, 1, 0, 0)));
        assets_Liabilities_log.add(createAssetLiabilityLog("신한은행 대출", 9000000.0, "new 부채", LocalDateTime.of(2023, 4, 23, 0, 0)));
        assets_Liabilities_log.add(createAssetLiabilityLog("법인차량", 50000000.0, "new 자산", LocalDateTime.of(2023, 5, 3, 0, 0)));
        assets_Liabilities_log.add(createAssetLiabilityLog("우리은행 대출", 10000000.0, "new 부채", LocalDateTime.of(2023, 9, 15, 0, 0)));
        assets_Liabilities_log.add(createAssetLiabilityLog("토지", 10000000.0, "감가상각", LocalDateTime.of(2024, 1, 1, 0, 0)));
        assets_Liabilities_log.add(createAssetLiabilityLog("건물", 110000000.0, "감가상각", LocalDateTime.of(2024, 1, 1, 0, 0)));
        assets_Liabilities_log.add(createAssetLiabilityLog("법인차량", 30000000.0, "감가상각", LocalDateTime.of(2024, 2, 10, 0, 0)));
        assets_Liabilities_log.add(createAssetLiabilityLog("농협은행 대출", 8000000.0, "new 부채", LocalDateTime.of(2024, 2, 10, 0, 0)));
        
        for (AssetLiabilityLog asset_liability_log : assets_Liabilities_log) {
            this.assetLiabilityLogRepository.save(asset_liability_log);
        }
    }
    
 // 자산&부채 로그 양식
    private AssetLiabilityLog createAssetLiabilityLog(String name, Double amount, String description, LocalDateTime date) {
        AssetLiabilityLog asset_liability_log = new AssetLiabilityLog();
        asset_liability_log.setName(name);
        asset_liability_log.setAmount(amount);
        asset_liability_log.setDescription(description);
        asset_liability_log.setDate(date);
        return asset_liability_log;
    }
}