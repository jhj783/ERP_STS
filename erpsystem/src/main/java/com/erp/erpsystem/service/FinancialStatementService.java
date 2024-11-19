package com.erp.erpsystem.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.erpsystem.db.AccountRepository;
import com.erp.erpsystem.db.AssetLiabilityLogRepository;
import com.erp.erpsystem.db.LiabilityRepository;

//@SpringBootTest
@Service
public class FinancialStatementService {

	@Autowired
    private AccountRepository accountRepository;
	
	@Autowired
    private AssetLiabilityLogRepository assetLiabilityLogRepository;
	
	@Autowired
    private  LiabilityRepository liabilityRepository;
	
	/*
	@Autowired
	private GetQuartersService getQuartersService;
	*/
	
	
    // 손익계산서.수익.매출액 // 
    public double getSales(LocalDateTime sDate, LocalDateTime eDate) {
        String type = "수익";

        Double totalAmount = accountRepository.findTotalAmountByTypeAndDateRange(type, sDate, eDate);

        return totalAmount != null ? totalAmount : 0.0;
    }
	
    
	// 손익계산서.비용.매출원가 // 
    public double getRawMaterials(LocalDateTime sDate, LocalDateTime eDate) {
        String description = "원자재구입";

        Double totalAmount = accountRepository.findTotalAmountByDescriptionAndDateRange(description, sDate, eDate);

        return totalAmount != null ? totalAmount : 0.0;
    }
	
    
	// 손익계산서.비용.판매비용 및 관리비 // 
	public double getCost(LocalDateTime sDate, LocalDateTime eDate) {
        String descriptionPrefix = "월급";
        List<String> descriptions = Arrays.asList("복리후생비", "물류비", "공과금", "임대료", "소모품비", "관리비");

        Double totalAmount = accountRepository.findTotalAmountByDescriptionCriteria(descriptionPrefix, descriptions, sDate, eDate);

        return totalAmount != null ? totalAmount : 0.0;
    }
	
	
	// 손익 계산서.비용.감가상각 // 
	// 현금흐름표.영업활동으로 인한 현금흐름.감가상각비 //
    public double getDepreciation(LocalDateTime sDate, LocalDateTime eDate) {
        Double results = assetLiabilityLogRepository.findNameAndAmountByDescriptionAndDateRange("감가상각", sDate, eDate);

        return results != null ? results : 0.0;
    }
	
    
	// 손익 계산서.이자 비용 // 
	public double getInterest(LocalDateTime sDate, LocalDateTime eDate) {
		Double totalInterestGenerated = assetLiabilityLogRepository.findTotalAmountByDescriptionAndDateRange("이자 발생", sDate, eDate);
	
	    return totalInterestGenerated != null ? totalInterestGenerated : 0.0;
	}
	
	
	// 손익 계산서.합계 비용 // 
	public double getTotalCost(LocalDateTime sDate, LocalDateTime eDate) {
		return getRawMaterials(sDate, eDate) + getCost(sDate, eDate) + getDepreciation(sDate, eDate) + getInterest(sDate, eDate);
	}
	
	
	// 손익 계산서.순이익 // 
	// 현금흐름표.영업활동으로 인한 현금흐름.순이익 //
	// 자본변동표.이익잉여금.순이익 //
	public double getNetProfit(LocalDateTime sDate, LocalDateTime eDate) {
		return getSales(sDate, eDate) - getTotalCost(sDate, eDate);
	}
	
	
	
	// 대차 대조표.자산.유동자산.현금 //
	public double getCash(LocalDateTime eDate) {
        Double currentCash = assetLiabilityLogRepository.findTotalAmountByCashDateRange("현금", eDate);
        
        return currentCash != null ? currentCash : 0.0;
	}
	
	
	// 대차 대조표.자산.유동자산.재고자산 //
	
	
	// 대차 대조표.자산.유동자산.합계 유동자산 //
	public double getCurrentAssets(LocalDateTime eDate) {
		return getCash(eDate); // +재고자산 + 매출채권 필요
	}
	
	
	// 대차 대조표.자산.비유동자산.list //
    public List<Object[]> getAssetLists(LocalDateTime eDate) {
        String description = "new 자산";
        List<String> names = Arrays.asList("토지", "건물", "법인차량");

        List<Object[]> assetLists = assetLiabilityLogRepository.findAmountsByDescriptionAndNames(description, names, eDate);

        return assetLists;
    }
	
	
	// 대차 대조표.자산.유동자산.감가상각 누계액 //
	public double getWriteDown(LocalDateTime eDate) {       
        Double currentWriteDown = assetLiabilityLogRepository.findTotalAmountByWriteDownDateRange("감가상각", eDate);
        
        return currentWriteDown != null ? currentWriteDown : 0.0;
	}
	
	
	// 대차 대조표.자산.비유동자산.합계 비유동자산 //
	public double getTotalNonCurrentAssets(LocalDateTime eDate) {
	    List<Object[]> assetLists = getAssetLists(eDate);
	    
	    double totalC_Asset = 0.0;
	    
	    if (assetLists != null && !assetLists.isEmpty()) {
	        for (Object[] asset : assetLists) {
	            totalC_Asset += (Double) asset[1];
	        }
	        
	        return totalC_Asset - getWriteDown(eDate);
	    }
	    
	    else {
	    	return 0.0;
	    }
	}
	
	
	// 대차 대조표.자산.총 자산 //
	public double getTotalAssets(LocalDateTime eDate) {
		return getCurrentAssets(eDate) + getTotalNonCurrentAssets(eDate);
	}
	
	
	// 대차 대조표.부채 및 자본.유동부채.단기차입금 //
	public double getShortTermDebt(LocalDateTime eDate) {
	    LocalDateTime futureDate = eDate.plusYears(1);
	    
	    List<String> shortTermDebtNames = liabilityRepository.findNameShortDebtNames(eDate, futureDate);
	    
	    Double shortTermDebt = assetLiabilityLogRepository.findShortTermDebt(shortTermDebtNames, eDate);
	    
	    return shortTermDebt != null ? shortTermDebt : 0.0;
	}
	
	
	// 대차 대조표.부채 및 자본.유동부채.합계 유동부채 //
	public double getCurrentLiabilities(LocalDateTime eDate) {
		return getShortTermDebt(eDate); // +매입채무 추가필요
	}
	
	
	// 대차 대조표.부채 및 자본.비유동부채.장기차입금 //
	public double getLongTermDebt(LocalDateTime eDate) {
	    LocalDateTime futureDate = eDate.plusYears(1);
	    
	    List<String> longTermDebtNames = liabilityRepository.findNameLongDebtNames(futureDate); 
	    
	    Double longTermDebt = assetLiabilityLogRepository.findShortTermDebt(longTermDebtNames, eDate);
	    
	    return longTermDebt != null ? longTermDebt : 0.0;
	}
	
	
	// 대차 대조표.부채 및 자본.비유동부채.합계 비유동부채 //
	public double getNonCurrentLiabilities(LocalDateTime eDate) {
		return getLongTermDebt(eDate);
	}
	
	
	// 대차 대조표.부채 및 자본.총 부채 //
	public double getTotalLiabilities(LocalDateTime eDate) {
		return getCurrentLiabilities(eDate) + getNonCurrentLiabilities(eDate) ;
	}

	
	// 대차 대조표.부채 및 자본.자본.자본금 //
	// 자본변동표.자본금 //
	public double getEquity(LocalDateTime eDate) {
	    List<String> equityNames = Arrays.asList("예치금", "현금");
	    
	    Double equity = assetLiabilityLogRepository.findEquity(equityNames, eDate);
	    
	    return equity != null ? equity : 0.0;
	}

	
	// 대차 대조표.부채 및 자본.자본.이익잉여금 //
	public double getRetainedEarnings(LocalDateTime eDate) {
		LocalDateTime firstDate = LocalDateTime.of(2023, 1, 1,0,0);
		
		return getNetProfit(firstDate, eDate);
	}
	
	
	// 대차 대조표.부채 및 자본.자본.총 자본 //
	public double getTotalCapital(LocalDateTime eDate) {
		return getEquity(eDate) + getRetainedEarnings(eDate);
	}
	
	
	// 대차 대조표.부채 및 자본.총 부채 및 자본 //
	public double getTotalLiabilityCapital(LocalDateTime eDate) {
		return getTotalLiabilities(eDate) + getTotalCapital(eDate);
	}
	
	
	
	// 현금흐름표.투자활동으로 인한 현금흐름 //
	public double getCashFlowFromInvestmentActivities(LocalDateTime sDate, LocalDateTime eDate) {
	    List<String> exclusionNames = Arrays.asList("예치금", "현금", "법인차량");

	    Double cashFlowFromInvestmentActivities = assetLiabilityLogRepository.findCashFlow(exclusionNames, sDate, eDate);

	    return cashFlowFromInvestmentActivities != null ? cashFlowFromInvestmentActivities : 0.0; 
	}


	// 현금흐름표.재무활동으로 인한 현금흐름 //
	public double getCashFlowFromFinancialActivities(LocalDateTime sDate, LocalDateTime eDate) {
		Double cashFlowFromFinancialActivities = assetLiabilityLogRepository.findCashFlowFinancial(sDate, eDate);

		return cashFlowFromFinancialActivities != null ? cashFlowFromFinancialActivities : 0.0 ;
	}
	
		
	
	// 자본변동표.이익잉여금.기초 이익잉여금 // 
	public double getBasicRetainedEarnings(LocalDateTime sDate, LocalDateTime eDate) {
		return getRetainedEarnings(eDate) - getNetProfit(sDate, eDate);
	}
	
	
	// 자본변동표.이익잉여금.기말 이익잉여금 //
	public double getEdRetainedEarnings(LocalDateTime eDate) {
		return getRetainedEarnings(eDate); // -배당금 추가필요
	}
	
	
	// 자본변동표.총 자본 //
	public double getAllCapital(LocalDateTime sDate, LocalDateTime eDate) {
		return getEquity(eDate) + getEdRetainedEarnings(eDate);
	}
	
	
    /*
    @Test
    void functionTest() {
    	LocalDateTime[] previousQuarter = getQuartersService.getPreviousQuartersDate();
    	LocalDateTime startDate = previousQuarter[0];
    	LocalDateTime endDate = previousQuarter[1];
    	
    	System.out.println();
    	System.out.println("매출액: "+getSales(startDate, endDate)); System.out.println();
    	System.out.println("원가: "+getRawMaterials(startDate,endDate));System.out.println();
    	System.out.println("비용: "+getCost(startDate,endDate));System.out.println();
    	System.out.println("감가상각: "+getDepreciation(startDate,endDate));System.out.println();
    	System.out.println("이자비용: "+getInterest(startDate,endDate));System.out.println();
    	System.out.println("합계비용: "+getTotalCost(startDate,endDate));System.out.println();
    	System.out.println("순이익: "+getNetProfit(startDate,endDate));System.out.println();
    	System.out.println("현금: "+getCash(endDate));System.out.println();
    	System.out.println("합계유동자산: "+getCurrentAssets(endDate));System.out.println();
    	System.out.println("비유동자산목록List: "+getAssetLists(endDate));System.out.println();
    	System.out.println("감가상각누계: "+getWriteDown(endDate));System.out.println();
    	System.out.println("합계비유동자산: "+getTotalNonCurrentAssets(endDate));System.out.println();
    	System.out.println("총자산: "+getTotalAssets(endDate));System.out.println();
    	System.out.println("단기차입금: "+getShortTermDebt(endDate));System.out.println();
    	System.out.println("합계유동부채: "+getCurrentLiabilities(endDate));System.out.println();
    	System.out.println("장기차입금: "+getLongTermDebt(endDate));System.out.println();
    	System.out.println("합계비유동부채: "+getNonCurrentLiabilities(endDate));System.out.println();
    	System.out.println("총부채: "+getTotalLiabilities(endDate));System.out.println();
    	System.out.println("자본금: "+getEquity(endDate));System.out.println();
    	System.out.println("이익잉여금: "+getRetainedEarnings(endDate));System.out.println();
    	System.out.println("총자본: "+getTotalCapital(endDate));System.out.println();
    	System.out.println("총부채및자본: "+getTotalLiabilityCapital(endDate));System.out.println();
    	System.out.println("투자활동: "+getCashFlowFromInvestmentActivities(startDate, endDate));System.out.println();
    	System.out.println("재무활동: "+getCashFlowFromFinancialActivities(startDate, endDate));System.out.println();
    	System.out.println("기초이익잉여금: "+getBasicRetainedEarnings(startDate, endDate));System.out.println();
    	System.out.println("기말이익잉여금: "+getEdRetainedEarnings(endDate));System.out.println();
    	System.out.println("총자본: "+getAllCapital(startDate, endDate));System.out.println();
    }
    */
}
