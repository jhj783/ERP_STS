package com.erp.erpsystem;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.erp.erpsystem.db.AccountRepository;
//import com.erp.erpsystem.db.Asset;
//import com.erp.erpsystem.db.AssetRepository;

@SpringBootTest
public class FinancialStatementService {

	@Autowired
    private AccountRepository accountRepository;
	
	/*@Autowired
    private AssetRepository assetRepository;

    @Test
    void getCashAndDepositTotalAmount() {
        // 현금과 예치금 자산을 개별적으로 가져오기
        Asset cashAsset = assetRepository.findByName("현금");
        Asset depositAsset = assetRepository.findByName("예치금");

        // 각각의 AMOUNT를 가져와 합산
        Double totalAmount = 0.0;
        if (cashAsset != null) {
            totalAmount += cashAsset.getAmount();
        }
        if (depositAsset != null) {
            totalAmount += depositAsset.getAmount();
        }

        //System.out.println("현금과 예치금의 AMOUNT 합계: " + totalAmount);
    }*/
    
	
    // 매출액 //
	@Test
    void getTotalRevenueAmountForDateRange() {
        String type = "수익";
        LocalDateTime startDate = LocalDateTime.of(2023, 9, 1,0,0);
        LocalDateTime endDate = LocalDateTime.of(2023, 12, 31,23,59);

        // 특정 날짜 범위와 TYPE이 "수익"인 AMOUNT 합계 구하기
        Double totalAmount = accountRepository.findTotalAmountByTypeAndDateRange(type, startDate, endDate);

        if (totalAmount != null) {
            System.out.println("2023년 9월 1일부터 12월 31일까지 '수익' TYPE의 AMOUNT 합계: " + totalAmount);
        } else {
            System.out.println("해당 기간에 대한 수익 데이터가 없습니다.");
        }
    }
	
	
	// 매출원가 //
	@Test
    void getTotalAmountForDescriptionInDateRange() {
        String description = "원자재구입";
        LocalDateTime startDate = LocalDateTime.of(2023, 9, 1,0,0);
        LocalDateTime endDate = LocalDateTime.of(2023, 12, 31,23,59);

        // 특정 기간과 DESCRIPTION이 "원자재구입"인 AMOUNT 합계 구하기
        Double totalAmount = accountRepository.findTotalAmountByDescriptionAndDateRange(description, startDate, endDate);

        if (totalAmount != null) {
            System.out.println("2023년 9월 1일부터 12월 31일까지 '원자재구입'의 AMOUNT 합계: " + totalAmount);
        } else {
            System.out.println("해당 기간에 대한 원자재구입 데이터가 없습니다.");
        }
    }
	
	
	// 판매비용 및 관리비 //
	@Test
    void getTotalAmountForSpecificDescriptionsInDateRange() {
        String descriptionPrefix = "월급";
        List<String> descriptions = Arrays.asList("복리후생비", "물류비", "공과금", "임대료", "소모품비", "관리비");
        LocalDateTime startDate = LocalDateTime.of(2023, 9, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 12, 31, 23, 59);

        // 특정 조건과 날짜 범위의 AMOUNT 합계 구하기
        Double totalAmount = accountRepository.findTotalAmountByDescriptionCriteria(descriptionPrefix, descriptions, startDate, endDate);

        if (totalAmount != null) {
            System.out.println("2023년 9월 1일부터 12월 31일까지 특정 조건의 AMOUNT 합계: " + totalAmount);
        } else {
            System.out.println("해당 조건에 맞는 데이터가 없습니다.");
        }
    }
	
}
