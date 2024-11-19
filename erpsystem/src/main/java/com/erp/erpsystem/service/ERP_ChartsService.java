package com.erp.erpsystem.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.erpsystem.db.AccountRepository;
import com.google.gson.Gson;

//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;


//@SpringBootTest
@Service
public class ERP_ChartsService {	
	@Autowired
	private AccountRepository AccountRepository;
	
	@Autowired
	private GetQuartersService getQuartersService; 
	
	@Autowired
	private FinancialStatementService financialStatementService;
	
	// 차트.운영비용 //
	public String getCostSummary(LocalDateTime sDate, LocalDateTime eDate) {
	    List<String> descriptions = List.of("복리후생비", "물류비", "공과금", "임대료", "소모품비", "관리비");
	    
	    List<Object[]> results = AccountRepository.findAccountSummaries(descriptions, sDate, eDate);
	    Map<String, Double> costSummaryMap = new HashMap<>();

	    for (Object[] result : results) {
	        String description = (String) result[0];
	        BigDecimal amount = (BigDecimal) result[1];
	        costSummaryMap.put(description, amount != null ? amount.doubleValue() : 0.0);
	    }

	    Gson gson = new Gson();
	    return gson.toJson(costSummaryMap);
	}
	
	
	// 차트.재무 비율 분석 //
	public String getFinancialRatios(LocalDateTime sDate, LocalDateTime eDate) {
		for ( LocalDateTime[] dates :getQuartersService.getPreviousQuartersDates()) {
			
		}
		
		return "a";
	}	
	
	// ROE(자기 자본 이익률) // 순이익x100 / ((기초자기자본+기말자기자본)/2)
	public Double getReturnOnEquity(LocalDateTime sDate, LocalDateTime eDate) {
		Double a=financialStatementService.getNetProfit(sDate, eDate);
		Double b=((financialStatementService.getTotalCapital(sDate) + financialStatementService.getTotalCapital(eDate))/2);
		
		return a*100/b;
	}
	
	// ROA(총 자산 이익률) // 순이익x100 / ((기초총자산+기말총자산)/2)
	public Double getReturnOnAssets(LocalDateTime sDate, LocalDateTime eDate) {
		Double a=financialStatementService.getNetProfit(sDate, eDate);
		Double b=(financialStatementService.getTotalAssets(sDate)+financialStatementService.getTotalAssets(eDate))/2;
		
		return a*100/b;
	}

	
	/*
	@Test
	void functionTest() {
		LocalDateTime[] previousQuarter = getQuartersService.getPreviousQuartersDate();
		System.out.println();
		System.out.println(getCostSummary(previousQuarter[0], previousQuarter[1]));
		System.out.println();
	}
	*/
}
