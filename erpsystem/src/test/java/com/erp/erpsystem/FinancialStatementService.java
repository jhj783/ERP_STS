package com.erp.erpsystem;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.erp.erpsystem.db.AccountRepository;
//import com.erp.erpsystem.db.AssetRepository;
import com.erp.erpsystem.db.AssetLiabilityLogRepository;
import com.erp.erpsystem.db.LiabilityRepository;

@SpringBootTest
public class FinancialStatementService {

	@Autowired
    private AccountRepository accountRepository;
	
	
	@Autowired
    private AssetLiabilityLogRepository assetLiabilityLogRepository;
	
	@Autowired
    private LiabilityRepository liabilityRepository;
	
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
    
	
    // 손익계산서.수익.매출액 //
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
	
	
	// 손익계산서.비용.매출원가 //
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
	
	
	// 손익계산서.비용.판매비용 및 관리비 //
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
	
	
	// 손익 계산서.비용.감가상각 //
	@Test
    void getDepreciationDetailsInDateRange() {
        LocalDateTime startDate = LocalDateTime.of(2023, 9, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 12, 31, 23, 59);

        // "감가상각"인 행의 AMOUNT와 NAME 조회
        List<Object[]> results = assetLiabilityLogRepository.findNameAndAmountByDescriptionAndDateRange("감가상각", startDate, endDate);

        if (results != null && !results.isEmpty()) {
            for (Object[] result : results) {
                String name = (String) result[0];
                Double amount = (Double) result[1];
                System.out.println("NAME: " + name + ", AMOUNT: " + amount);
            }
        } else {
            System.out.println("해당 기간에 대한 감가상각 데이터가 없습니다.");
        }
    }
	
	
	// 손익 계산서.이자 비용 //
	@Test
    void getTotalInterestGeneratedAmountForDateRange() {
        LocalDateTime startDate = LocalDateTime.of(2023, 9, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 12, 31, 23, 59);

        Double totalInterestGenerated = assetLiabilityLogRepository.findTotalAmountByDescriptionAndDateRange("이자 발생", startDate, endDate);

        if (totalInterestGenerated != null) {
            System.out.println("2023년 9월 1일부터 12월 31일까지 '이자 발생' DESCRIPTION의 AMOUNT 합계: " + totalInterestGenerated);
        } else {
            System.out.println("해당 기간에 대한 이자 발생 데이터가 없습니다.");
        }
    }
	
	
	
	
	// 대차 대조표.자산.유동자산.현금 //
	@Test
	void getCash() {
        LocalDateTime endDate = LocalDateTime.of(2023, 12, 31, 23, 59);
        
        Double currentCash = assetLiabilityLogRepository.findTotalAmountByCashDateRange("현금", endDate);
        
        System.out.println("2023 12월 31일까지의 현금: " + currentCash);
	}
	
	
	// 대차 대조표.자산.유동자산.재고자산 //
	
	
	// 대차 대조표.자산.비유동자산.list //
	@Test
    void getNewAssetAmounts() {
        String description = "new 자산";
        List<String> names = Arrays.asList("토지", "건물", "법인차량");

        // "new 자산"이고 NAME이 "토지", "건물", "법인차량"인 항목들의 AMOUNT 리스트 조회
        List<Double> amounts = assetLiabilityLogRepository.findAmountsByDescriptionAndNames(description, names);

        if (amounts != null && !amounts.isEmpty()) {
            System.out.println("'new 자산'이고 NAME이 '토지', '건물', '법인차량'인 AMOUNT 리스트: " + amounts);
        } else {
            System.out.println("해당 조건에 맞는 데이터가 없습니다.");
        }
    }
	
	
	// 대차 대조표.자산.유동자산.감가상각 누계액 //
	@Test
	void getWriteDown() {
        LocalDateTime endDate = LocalDateTime.of(2023, 12, 31, 23, 59);
        
        Double currentWriteDown = assetLiabilityLogRepository.findTotalAmountByWriteDownDateRange("감가상각", endDate);
        
        System.out.println("2023 12월 31일까지의 감가상각의 합: " + currentWriteDown);
	}
	
	
	// 대차 대조표.부채 및 자본.유동부채.단기차입금 //
	@Test
	void getShortTermDebt() {
	    LocalDateTime endDate = LocalDateTime.of(2023, 12, 31, 23, 59);
	    LocalDateTime futureDate = endDate.plusYears(1);
	    
	    List<String> shortTermDebtNames = liabilityRepository.findNameShortDebtNames(endDate, futureDate);
	    
	    Double shortTermDebt = assetLiabilityLogRepository.findShortTermDebt(shortTermDebtNames, endDate);
	    
	    System.out.println("단기차입금: "+shortTermDebt);
	}
	
	
	// 대차 대조표.부채 및 자본.유동부채.장기차입금 //
	@Test
	void getLongTermDebt() {
	    LocalDateTime endDate = LocalDateTime.of(2023, 12, 31, 23, 59);
	    LocalDateTime futureDate = endDate.plusYears(1);
	    
	    List<String> longTermDebtNames = liabilityRepository.findNameLongDebtNames(futureDate); 
	    
	    Double longTermDebt = assetLiabilityLogRepository.findShortTermDebt(longTermDebtNames, endDate);
	    
	    System.out.println("장기차입금: "+longTermDebt);
	}

	
	// 대차 대조표.부채 및 자본.자본.자본금 //
	@Test
	void getEquity() {
	    LocalDateTime endDate = LocalDateTime.of(2023, 12, 31, 23, 59); 
	    List<String> equityNames = Arrays.asList("예치금", "현금");
	    
	    Double equity = assetLiabilityLogRepository.findEquity(equityNames, endDate);
	    
	    System.out.println("자본금: " + equity);
	}

	
	
	// 현금흐름표.영업활동으로 인한 현금흐름.순이익 // 매출액-매출원가
	
	
	
	// 대차 대조표.부채 및 자본.자본.이익잉여금 // 처음부터 순이익+이번분기 순이익
	
	
	
	// 현금흐름표.투자활동으로 인한 현금흐름 //
	@Test
	void getCashFlowFromInvestmentActivities() {
	    LocalDateTime startDate = LocalDateTime.of(2023, 9, 1, 0, 0);
	    LocalDateTime endDate = LocalDateTime.of(2023, 12, 31, 23, 59);
	    List<String> exclusionNames = Arrays.asList("예치금", "현금", "법인차량");

	    Double cashFlowFromInvestmentActivities = assetLiabilityLogRepository.findCashFlow(exclusionNames, startDate, endDate);

	    if (cashFlowFromInvestmentActivities != null) {
	        System.out.println("투자활동으로 인한 현금흐름: " + cashFlowFromInvestmentActivities);
	    } else {
	        System.out.println("해당 조건에 맞는 데이터가 없습니다.");
	    }
	}


	// 현금흐름표.재무활동으로 인한 현금흐름 //
		@Test
		void getCashFlowFromFinancialActivities() {
		    LocalDateTime startDate = LocalDateTime.of(2023, 9, 1, 0, 0);
		    LocalDateTime endDate = LocalDateTime.of(2023, 12, 31, 23, 59);

		    Double cashFlowFromFinancialActivities = assetLiabilityLogRepository.findCashFlowFinancial(startDate, endDate);

		    if (cashFlowFromFinancialActivities != null) {
		        System.out.println("재무활동으로 인한 현금흐름: " + cashFlowFromFinancialActivities);
		    } else {
		        System.out.println("해당 조건에 맞는 데이터가 없습니다.");
		    }
		}
	
		
	// 자본변동표.이익잉여금.기초 이익잉여금 // 

		
		
}
