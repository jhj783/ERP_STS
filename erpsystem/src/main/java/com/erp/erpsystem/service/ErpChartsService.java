package com.erp.erpsystem.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.erpsystem.db.AccountRepository;
//import com.google.gson.Gson;

//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
@Service
public class ErpChartsService {
	@Autowired
	private AccountRepository AccountRepository;

	@Autowired
	private GetQuartersService getQuartersService;

	@Autowired
	private FinancialStatementService financialStatementService;

	// 차트.매출액(막대)&순이익(꺾은선) //
	public Map<String, List<BigDecimal>> getSalesAndNetProfit(LocalDateTime sDate, LocalDateTime eDate) {
		List<BigDecimal> salesList = new ArrayList<>();
		List<BigDecimal> netProfitList = new ArrayList<>();

		for (LocalDateTime[] dates : getQuartersService.getPreviousQuartersDates()) {
			salesList.add(financialStatementService.getSales(dates[0], dates[1]));
			netProfitList.add(financialStatementService.getNetProfit(dates[0], dates[1]));
		}

		Map<String, List<BigDecimal>> salesAndNetProfits = new HashMap<>();
		salesAndNetProfits.put("Sales", salesList);
		salesAndNetProfits.put("NetProfit", netProfitList);

		return salesAndNetProfits;
	}

	// 차트.재무 비율 분석 //
	public Map<String, List<BigDecimal>> getFinancialRatios(LocalDateTime sDate, LocalDateTime eDate) {
		List<BigDecimal> roeList = new ArrayList<>();
		List<BigDecimal> roaList = new ArrayList<>();

		for (LocalDateTime[] dates : getQuartersService.getPreviousQuartersDates()) {
			roeList.add(getReturnOnEquity(dates[0], dates[1]));
			roaList.add(getReturnOnAssets(dates[0], dates[1]));
		}

		Map<String, List<BigDecimal>> financialRatios = new HashMap<>();
		financialRatios.put("ROEs", roeList);
		financialRatios.put("ROAs", roaList);

		return financialRatios;
	}
	// ROE(자기 자본 이익률) // 순이익 x 100 / ((기초자기자본 + 기말자기자본) / 2)
	private BigDecimal getReturnOnEquity(LocalDateTime sDate, LocalDateTime eDate) {
	    BigDecimal netProfit = financialStatementService.getNetProfit(sDate, eDate);
	    BigDecimal initialCapital = financialStatementService.getTotalCapital(sDate);
	    BigDecimal finalCapital = financialStatementService.getTotalCapital(eDate);

	    // (기초자기자본 + 기말자기자본) / 2
	    BigDecimal averageCapital = initialCapital.add(finalCapital).divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);

	    // (순이익 x 100) / 평균 자본
	    return netProfit.multiply(BigDecimal.valueOf(100)).divide(averageCapital, 2, RoundingMode.HALF_UP);
	}
	// ROA(총 자산 이익률) // 순이익 x 100 / ((기초총자산 + 기말총자산) / 2)
	private BigDecimal getReturnOnAssets(LocalDateTime sDate, LocalDateTime eDate) {
	    BigDecimal netProfit = financialStatementService.getNetProfit(sDate, eDate);
	    BigDecimal initialAssets = financialStatementService.getTotalAssets(sDate);
	    BigDecimal finalAssets = financialStatementService.getTotalAssets(eDate);

	    // (기초총자산 + 기말총자산) / 2
	    BigDecimal averageAssets = initialAssets.add(finalAssets).divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);

	    // (순이익 x 100) / 평균 총자산
	    return netProfit.multiply(BigDecimal.valueOf(100)).divide(averageAssets, 2, RoundingMode.HALF_UP);
	}


	// 차트.운영비용 //
	public Map<String, BigDecimal> getCostSummary(LocalDateTime sDate, LocalDateTime eDate) {
		List<String> descriptions = List.of("복리후생비", "물류비", "공과금", "임대료", "소모품비", "관리비");

		List<Object[]> results = AccountRepository.findAccountSummaries(descriptions, sDate, eDate);
		Map<String, BigDecimal> costSummaryMap = new HashMap<>();

		for (Object[] result : results) {
			String description = (String) result[0];
			BigDecimal amount = (BigDecimal) result[1];
			costSummaryMap.put(description, amount != null ? amount : BigDecimal.ZERO);
		}

		return costSummaryMap;
	}

	// 차트.자본&부채 비율 //
	public Map<String, BigDecimal> getCapitalLiabilityRatio(LocalDateTime sDate, LocalDateTime eDate) {
		Map<String, BigDecimal> capitalLiabilityRatio = new HashMap<>();
		capitalLiabilityRatio.put("Capital",
				financialStatementService.getTotalCapital(getQuartersService.getPreviousQuartersDate()[1]));
		capitalLiabilityRatio.put("Libility",
				financialStatementService.getTotalLiabilities(getQuartersService.getPreviousQuartersDate()[1]));

		return capitalLiabilityRatio;
	}

	// 차트.부채 //
	public List<BigDecimal> getLiabilities(LocalDateTime sDate, LocalDateTime eDate) {
		List<BigDecimal> liabilities = new ArrayList<>();

		for (LocalDateTime[] dates : getQuartersService.getPreviousQuartersDates()) {
			liabilities.add(financialStatementService.getTotalLiabilities(dates[1]));
		}
		return liabilities;
	}

	/*
	 * @Test void functionTest() { LocalDateTime[] previousQuarter =
	 * getQuartersService.getPreviousQuartersDate(); System.out.println();
	 * System.out.println(getCostSummary(previousQuarter[0], previousQuarter[1]));
	 * System.out.println();
	 * System.out.println(getCapitalLiabilityRatio(previousQuarter[0],
	 * previousQuarter[1])); System.out.println();
	 * System.out.println(getFinancialRatios(previousQuarter[0],
	 * previousQuarter[1])); System.out.println();
	 * System.out.println(getSalesAndNetProfit(previousQuarter[0],
	 * previousQuarter[1])); System.out.println();
	 * System.out.println(getLiabilities(previousQuarter[0], previousQuarter[1]));
	 * System.out.println(); }
	 */
}
