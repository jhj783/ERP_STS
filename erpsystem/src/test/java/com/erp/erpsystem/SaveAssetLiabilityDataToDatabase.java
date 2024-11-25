package com.erp.erpsystem;

import java.math.BigDecimal;
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
import com.erp.erpsystem.db.Stock;
import com.erp.erpsystem.db.StockRepository;
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
    
    @Autowired
    private StockRepository stockRepository;

    @Test
    void testJpa() {
        // 자산 내용
        List<Asset> assets = new ArrayList<>();

        assets.add(createAsset("현금", BigDecimal.valueOf(10000000.0), "유동", LocalDateTime.of(2023, 1, 1, 0, 0),
                BigDecimal.valueOf(10000000.0)));
        assets.add(createAsset("예치금", BigDecimal.valueOf(100000000.0), "유동", LocalDateTime.of(2023, 1, 1, 0, 0),
                BigDecimal.valueOf(100000000.0)));
        assets.add(createAsset("토지", BigDecimal.valueOf(40000000.0), "비유동", LocalDateTime.of(2023, 1, 1, 0, 0),
                BigDecimal.valueOf(30000000.0)));
        assets.add(createAsset("건물", BigDecimal.valueOf(110000000.0), "비유동", LocalDateTime.of(2023, 1, 1, 0, 0),
                BigDecimal.valueOf(100000000.0)));
        assets.add(createAsset("법인차량", BigDecimal.valueOf(50000000.0), "비유동", LocalDateTime.of(2023, 5, 3, 0, 0),
                BigDecimal.valueOf(50000000.0)));

        for (Asset asset : assets) {
            this.assetRepository.save(asset);
        }

        // 부채 내용
        List<Liability> liabilities = new ArrayList<>();

        liabilities.add(createLiability("신한은행 대출", BigDecimal.valueOf(8850000.0), "유동", BigDecimal.valueOf(5000000.0),
                LocalDateTime.of(2023, 1, 1, 0, 0), BigDecimal.valueOf(3.5), "매월",
                LocalDateTime.of(2025, 1, 1, 0, 0)));
        liabilities.add(createLiability("하나은행 대출", BigDecimal.valueOf(7173000.0), "유동", BigDecimal.valueOf(4500000.0),
                LocalDateTime.of(2023, 4, 23, 0, 0), BigDecimal.valueOf(3.3), "매월",
                LocalDateTime.of(2025, 4, 23, 0, 0)));
        liabilities.add(createLiability("우리은행 대출", BigDecimal.valueOf(6885000.0), "유동", BigDecimal.valueOf(5000000.0),
                LocalDateTime.of(2023, 9, 15, 0, 0), BigDecimal.valueOf(2.9), "매월",
                LocalDateTime.of(2026, 3, 15, 0, 0)));
        liabilities.add(createLiability("농협은행 대출", BigDecimal.valueOf(5593500.0), "유동", BigDecimal.valueOf(4500000.0),
                LocalDateTime.of(2024, 2, 10, 0, 0), BigDecimal.valueOf(2.7), "매월",
                LocalDateTime.of(2026, 2, 10, 0, 0)));

        for (Liability liability : liabilities) {
            this.liabilityRepository.save(liability);
        }

        // 자산&부채 로그 내용
        List<AssetLiabilityLog> assets_Liabilities_log = new ArrayList<>();

        assets_Liabilities_log
                .add(createAssetLiabilityLog("현금", BigDecimal.valueOf(10000000.0), "new 자산", LocalDateTime.of(2023, 1, 1, 0, 0)));
        assets_Liabilities_log.add(
                createAssetLiabilityLog("예치금", BigDecimal.valueOf(100000000.0), "new 자산", LocalDateTime.of(2023, 1, 1, 0, 0)));
        assets_Liabilities_log
                .add(createAssetLiabilityLog("토지", BigDecimal.valueOf(30000000.0), "new 자산", LocalDateTime.of(2023, 1, 1, 0, 0)));
        assets_Liabilities_log
                .add(createAssetLiabilityLog("건물", BigDecimal.valueOf(100000000.0), "new 자산", LocalDateTime.of(2023, 1, 1, 0, 0)));
        assets_Liabilities_log.add(
                createAssetLiabilityLog("신한은행 대출", BigDecimal.valueOf(5000000.0), "new 부채", LocalDateTime.of(2023, 1, 1, 0, 0)));
        assets_Liabilities_log.add(
                createAssetLiabilityLog("법인차량", BigDecimal.valueOf(50000000.0), "new 자산", LocalDateTime.of(2023, 5, 3, 0, 0)));

        for (AssetLiabilityLog asset_liability_log : assets_Liabilities_log) {
            this.assetLiabilityLogRepository.save(asset_liability_log);
        }
        
        // 재고 내용
        List<Stock> stocks = new ArrayList<>();
        
        stocks.add(createStock("M5A3", BigDecimal.valueOf(1150450), 2150, "AR", 8));
        stocks.add(createStock("AM40", BigDecimal.valueOf(930540), 511, "AR", 4));
        stocks.add(createStock("AK-24", BigDecimal.valueOf(731180), 923, "AR", 3));
        stocks.add(createStock("SFAR-M GL", BigDecimal.valueOf(1873910), 130, "AR", 3));
        stocks.add(createStock("AC-42", BigDecimal.valueOf(914990), 328, "AR", 4));
        stocks.add(createStock("RM-68", BigDecimal.valueOf(1130400), 1221, "AR", 6));
        stocks.add(createStock("GEW-46", BigDecimal.valueOf(1199990), 922, "AR", 5));
        stocks.add(createStock("VHX-D3", BigDecimal.valueOf(1130200), 882, "AR", 7));
        stocks.add(createStock("AK 5C", BigDecimal.valueOf(924000), 762, "AR", 7));
        stocks.add(createStock("M16A3", BigDecimal.valueOf(790340), 1541, "AR", 5));
        stocks.add(createStock("ACW-R", BigDecimal.valueOf(849420), 599, "AR", 5));
        stocks.add(createStock("A-91", BigDecimal.valueOf(640000), 396, "AR", 5));
        stocks.add(createStock("M416", BigDecimal.valueOf(894300), 1277, "AR", 4));
        stocks.add(createStock("MTAR-21", BigDecimal.valueOf(880200), 641, "AR", 7));
        stocks.add(createStock("AEK-971", BigDecimal.valueOf(715000), 971, "AR", 6));
        
        for(Stock stock : stocks) {
        	this.stockRepository.save(stock);
        }
        
    }
    


    // 자산 양식
    private Asset createAsset(String name, BigDecimal amount, String type, LocalDateTime date, BigDecimal acquisitionCost) {
        Asset asset = new Asset();
        asset.setName(name);
        asset.setAmount(amount);
        asset.setType(type);
        asset.setDate(date);
        asset.setAcquisitionCost(acquisitionCost);
        return asset;
    }

    // 부채 양식
    private Liability createLiability(String name, BigDecimal currentValue, String type, BigDecimal originValue,
            LocalDateTime localDateTime, BigDecimal interestRate, String interestPeriod, LocalDateTime localDateTime2) {
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
    private AssetLiabilityLog createAssetLiabilityLog(String name, BigDecimal amount, String description,
            LocalDateTime date) {
        AssetLiabilityLog asset_liability_log = new AssetLiabilityLog();
        asset_liability_log.setName(name);
        asset_liability_log.setAmount(amount);
        asset_liability_log.setDescription(description);
        asset_liability_log.setDate(date);
        return asset_liability_log;
    }
    
    // 재고 양식
    private Stock createStock(String name, BigDecimal price, int quantity, String category, int reorderlevel) {
    	Stock stock = new Stock();
    	stock.setName(name);
    	stock.setPrice(price);
    	stock.setQuantity(quantity);
    	stock.setCategory(category);
    	stock.setReorderLevel(reorderlevel);
    	return stock;
    }
}
