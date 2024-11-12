package com.erp.erpsystem;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.erp.erpsystem.db.Asset;
import com.erp.erpsystem.db.AssetRepository;
import com.erp.erpsystem.db.Liability;
import com.erp.erpsystem.db.LiabilityRepository;
import com.erp.erpsystem.db.AssetLiabilityLog;
import com.erp.erpsystem.db.AssetLiabilityLogRepository;


@SpringBootTest
class SaveAssetLiabilityDataToDatabase {

    @Autowired
    private AssetRepository assetRepository;
    
    @Autowired
    private LiabilityRepository liabilityRepository;
    
    @Autowired
    private AssetLiabilityLogRepository assetLiabilityLogRepository;

    @Test
    void testJpa() {
    	//자산 내용
        List<Asset> assets = new ArrayList<>();

        assets.add(createAsset("현금", 10000000.0, "유동", LocalDateTime.of(2023, 1, 1, 0, 0), 10000000.0));
        assets.add(createAsset("예치금", 1000000000.0, "유동", LocalDateTime.of(2023, 1, 1, 0, 0), 100000000.0));
        assets.add(createAsset("토지", 40000000.0, "비유동", LocalDateTime.of(2023, 1, 1, 0, 0), 30000000.0));
        assets.add(createAsset("건물", 110000000.0, "비유동", LocalDateTime.of(2023, 1, 1, 0, 0), 100000000.0));
        assets.add(createAsset("법인차량", 30000000.0, "비유동", LocalDateTime.of(2023, 5, 3, 0, 0), 50000000.0));
        
        for (Asset asset : assets) {
            this.assetRepository.save(asset);
        }
        
        //부채 내용
        List<Liability> liabilities = new ArrayList<>();

        liabilities.add(createLiability("신한은행 대출", 8850000.0, "유동", 5000000.0, LocalDateTime.of(2023, 1, 1,0,0), 3.5, "매월", LocalDateTime.of(2025, 1, 1,0,0)));
        liabilities.add(createLiability("하나은행 대출", 7173000.0, "유동", 4500000.0, LocalDateTime.of(2023, 4, 23,0,0), 3.3, "매월", LocalDateTime.of(2025, 4, 23,0,0)));
        liabilities.add(createLiability("우리은행 대출", 6885000.0, "유동", 5000000.0, LocalDateTime.of(2023, 9, 15,0,0), 2.9, "매월", LocalDateTime.of(2026, 3, 15,0,0)));
        liabilities.add(createLiability("농협은행 대출", 5593500.0, "유동", 4500000.0, LocalDateTime.of(2024, 2, 10,0,0), 2.7, "매월", LocalDateTime.of(2026, 2, 10,0,0)));
        
        for (Liability liability : liabilities) {
            this.liabilityRepository.save(liability);
        }
        
        //자산&부채 로그 내용
        List<AssetLiabilityLog> assets_Liabilities_log = new ArrayList<>();

        assets_Liabilities_log.add(createAssetLiabilityLog("현금", 10000000.0, "new 자산", LocalDateTime.of(2023, 1, 1, 0, 0)));
        assets_Liabilities_log.add(createAssetLiabilityLog("예치금", 100000000.0, "new 자산", LocalDateTime.of(2023, 1, 1, 0, 0)));
        assets_Liabilities_log.add(createAssetLiabilityLog("토지", 30000000.0, "new 자산", LocalDateTime.of(2023, 1, 1, 0, 0)));
        assets_Liabilities_log.add(createAssetLiabilityLog("건물", 100000000.0, "new 자산", LocalDateTime.of(2023, 1, 1, 0, 0)));
        assets_Liabilities_log.add(createAssetLiabilityLog("신한은행 대출", 5000000.0, "new 부채", LocalDateTime.of(2023, 1, 1, 0, 0)));
        assets_Liabilities_log.add(createAssetLiabilityLog("신한은행 대출", 4500000.0, "new 부채", LocalDateTime.of(2023, 4, 23, 0, 0)));
        assets_Liabilities_log.add(createAssetLiabilityLog("법인차량", 50000000.0, "new 자산", LocalDateTime.of(2023, 5, 3, 0, 0)));
        assets_Liabilities_log.add(createAssetLiabilityLog("우리은행 대출", 5000000.0, "new 부채", LocalDateTime.of(2023, 9, 15, 0, 0)));
        assets_Liabilities_log.add(createAssetLiabilityLog("토지", 1022100.0, "감가상각", LocalDateTime.of(2024, 1, 1, 0, 0)));
        assets_Liabilities_log.add(createAssetLiabilityLog("건물", 11030440.0, "감가상각", LocalDateTime.of(2024, 1, 1, 0, 0)));
        assets_Liabilities_log.add(createAssetLiabilityLog("법인차량", -7706500.0, "감가상각", LocalDateTime.of(2024, 1, 1, 0, 0)));
        assets_Liabilities_log.add(createAssetLiabilityLog("농협은행 대출", 4500000.0, "new 부채", LocalDateTime.of(2024, 2, 10, 0, 0)));
        
        for (AssetLiabilityLog asset_liability_log : assets_Liabilities_log) {
            this.assetLiabilityLogRepository.save(asset_liability_log);
        }
        
    }
    

    // 자산 양식
    private Asset createAsset(String name, Double amount, String type, LocalDateTime date, Double acquisitionCost) {
        Asset asset = new Asset();
        asset.setName(name);
        asset.setAmount(amount);
        asset.setType(type);
        asset.setDate(date);
        asset.setAcquisitionCost(acquisitionCost);
        return asset;
    }
    
    // 부채 양식
    private Liability createLiability(String name, Double currentValue, String type, Double originValue, LocalDateTime localDateTime, Double interestRate, String interestPeriod, LocalDateTime localDateTime2) {
        Liability liability = new Liability();
        liability.setName(name);
        liability.setCurrentValue(currentValue);
        liability.setType(type);
        liability.setOriginValue(originValue);
        liability.setDate(localDateTime);
        liability.setInterestRate(interestRate);
        liability.setInterestPeriod(interestPeriod);
        liability.setMaturityDate(localDateTime2);
        return liability;
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
