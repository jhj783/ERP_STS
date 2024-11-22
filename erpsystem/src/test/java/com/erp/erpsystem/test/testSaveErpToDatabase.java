package com.erp.erpsystem.test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.erp.erpsystem.db.AssetLiabilityLog;
import com.erp.erpsystem.db.AssetLiabilityLogRepository;

@SpringBootTest
class TestSaveErpToDatabase {

    @Autowired
    private AssetLiabilityLogRepository assetLiabilityLogRepository;

    @Test
    void testJpa() {
        // 자산&부채 로그 내용
        List<AssetLiabilityLog> assetsLiabilitiesLog = new ArrayList<>();

        assetsLiabilitiesLog.add(createAssetLiabilityLog("현금", new BigDecimal("10000000"), "new 자산", LocalDateTime.of(2023, 1, 1, 0, 0)));
        assetsLiabilitiesLog.add(createAssetLiabilityLog("예치금", new BigDecimal("100000000"), "new 자산", LocalDateTime.of(2023, 1, 1, 0, 0)));
        assetsLiabilitiesLog.add(createAssetLiabilityLog("토지", new BigDecimal("30000000"), "new 자산", LocalDateTime.of(2023, 1, 1, 0, 0)));
        assetsLiabilitiesLog.add(createAssetLiabilityLog("건물", new BigDecimal("100000000"), "new 자산", LocalDateTime.of(2023, 1, 1, 0, 0)));
        assetsLiabilitiesLog.add(createAssetLiabilityLog("신한은행 대출", new BigDecimal("10000000"), "new 부채", LocalDateTime.of(2023, 1, 1, 0, 0)));
        assetsLiabilitiesLog.add(createAssetLiabilityLog("신한은행 대출", new BigDecimal("9000000"), "new 부채", LocalDateTime.of(2023, 4, 23, 0, 0)));
        assetsLiabilitiesLog.add(createAssetLiabilityLog("법인차량", new BigDecimal("50000000"), "new 자산", LocalDateTime.of(2023, 5, 3, 0, 0)));
        assetsLiabilitiesLog.add(createAssetLiabilityLog("우리은행 대출", new BigDecimal("10000000"), "new 부채", LocalDateTime.of(2023, 9, 15, 0, 0)));
        assetsLiabilitiesLog.add(createAssetLiabilityLog("토지", new BigDecimal("10000000"), "감가상각", LocalDateTime.of(2024, 1, 1, 0, 0)));
        assetsLiabilitiesLog.add(createAssetLiabilityLog("건물", new BigDecimal("110000000"), "감가상각", LocalDateTime.of(2024, 1, 1, 0, 0)));
        assetsLiabilitiesLog.add(createAssetLiabilityLog("법인차량", new BigDecimal("30000000"), "감가상각", LocalDateTime.of(2024, 2, 10, 0, 0)));
        assetsLiabilitiesLog.add(createAssetLiabilityLog("농협은행 대출", new BigDecimal("8000000"), "new 부채", LocalDateTime.of(2024, 2, 10, 0, 0)));

        for (AssetLiabilityLog assetLiabilityLog : assetsLiabilitiesLog) {
            this.assetLiabilityLogRepository.save(assetLiabilityLog);
        }
    }

    // 자산&부채 로그 양식
    private AssetLiabilityLog createAssetLiabilityLog(String name, BigDecimal amount, String description, LocalDateTime date) {
        AssetLiabilityLog assetLiabilityLog = new AssetLiabilityLog();
        assetLiabilityLog.setName(name);
        assetLiabilityLog.setAmount(amount);
        assetLiabilityLog.setDescription(description);
        assetLiabilityLog.setDate(date);
        return assetLiabilityLog;
    }
}
