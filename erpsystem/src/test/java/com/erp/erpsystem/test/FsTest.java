package com.erp.erpsystem.test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.erpsystem.db.AccountRepository;
import com.erp.erpsystem.db.AssetLiabilityLogRepository;
import com.erp.erpsystem.db.LiabilityRepository;
import com.erp.erpsystem.service.GetQuartersService;

@SpringBootTest

public class FsTest {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AssetLiabilityLogRepository assetLiabilityLogRepository;

	@Autowired
	private LiabilityRepository liabilityRepository;

	
	 @Autowired private GetQuartersService getQuartersService;
	 

	// 손익계산서.수익.매출액 //
	public BigDecimal getSales(LocalDateTime sDate, LocalDateTime eDate) {
		String type = "수익";

		BigDecimal totalAmount = accountRepository.findTotalAmountByTypeAndDateRange(type, sDate, eDate);

		return totalAmount != null ? totalAmount : BigDecimal.ZERO;
	}

	// 손익계산서.비용.매출원가 //
	public BigDecimal getRawMaterials(LocalDateTime sDate, LocalDateTime eDate) {
		String description = "원자재구입";

		BigDecimal totalAmount = accountRepository.findTotalAmountByDescriptionAndDateRange(description, sDate, eDate);

		return totalAmount != null ? totalAmount : BigDecimal.ZERO;
	}

	// 손익계산서.비용.판매비용 및 관리비 //
	public BigDecimal getCost(LocalDateTime sDate, LocalDateTime eDate) {
		String descriptionPrefix = "월급";
		List<String> descriptions = Arrays.asList("복리후생비", "물류비", "공과금", "임대료", "소모품비", "관리비");

		BigDecimal totalAmount = accountRepository.findTotalAmountByDescriptionCriteria(descriptionPrefix, descriptions,
				sDate, eDate);

		return totalAmount != null ? totalAmount : BigDecimal.ZERO;
	}

	// 손익 계산서.비용.감가상각 //
	// 현금흐름표.영업활동으로 인한 현금흐름.감가상각비 //
	public BigDecimal getDepreciation(LocalDateTime sDate, LocalDateTime eDate) {
		BigDecimal results = assetLiabilityLogRepository.findNameAndAmountByDescriptionAndDateRange("감가상각", sDate,
				eDate);

		return results != null ? results : BigDecimal.ZERO;
	}
	
	// 현금흐름표.영업활동으로 인한 현금흐름.합계 영업활동 현금흐름 // +매출채권 +매입채무 필요
	public BigDecimal getTotalSalesActivityFlow(LocalDateTime sDate, LocalDateTime eDate) {
		return getDepreciation(sDate, eDate);
	}

	// 손익 계산서.이자 비용 //
	public BigDecimal getInterest(LocalDateTime sDate, LocalDateTime eDate) {
		BigDecimal totalInterestGenerated = assetLiabilityLogRepository
				.findTotalAmountByDescriptionAndDateRange("이자 발생", sDate, eDate);

		return totalInterestGenerated != null ? totalInterestGenerated : BigDecimal.ZERO;
	}

	// 손익 계산서.합계 비용 //
	public BigDecimal getTotalCost(LocalDateTime sDate, LocalDateTime eDate) {
		BigDecimal total = BigDecimal.ZERO;

		BigDecimal[] values = { getRawMaterials(sDate, eDate), getCost(sDate, eDate), getDepreciation(sDate, eDate),
				getInterest(sDate, eDate) };

		for (BigDecimal value : values) {
			total = total.add(value);
		}
		return total;
	}

	// 손익 계산서.순이익 //
	// 현금흐름표.영업활동으로 인한 현금흐름.순이익 //
	// 자본변동표.이익잉여금.순이익 //
	public BigDecimal getNetProfit(LocalDateTime sDate, LocalDateTime eDate) {
		return getSales(sDate, eDate).subtract(getTotalCost(sDate, eDate));
	}

	// 대차 대조표.자산.유동자산.현금 //
	public BigDecimal getCash(LocalDateTime eDate) {
		BigDecimal currentCash = assetLiabilityLogRepository.findTotalAmountByCashDateRange("현금", eDate);

		return currentCash != null ? currentCash : BigDecimal.ZERO;
	}

	// 대차 대조표.자산.유동자산.재고자산 //

	// 대차 대조표.자산.유동자산.합계 유동자산 //
	public BigDecimal getCurrentAssets(LocalDateTime eDate) {
		return getCash(eDate); // +재고자산 + 매출채권 필요
	}

	// 대차 대조표.자산.비유동자산.list //
	public List<Object[]> getAssetLists(LocalDateTime eDate) {
		String description = "new 자산";
		List<String> names = Arrays.asList("토지", "건물", "법인차량");

		List<Object[]> assetLists = assetLiabilityLogRepository.findAmountsByDescriptionAndNames(description, names,
				eDate);

		return assetLists;
	}

	// 대차 대조표.자산.유동자산.감가상각 누계액 //
	public BigDecimal getWriteDown(LocalDateTime eDate) {
		BigDecimal currentWriteDown = assetLiabilityLogRepository.findTotalAmountByWriteDownDateRange("감가상각", eDate);

		return currentWriteDown != null ? currentWriteDown : BigDecimal.ZERO;
	}

	// 대차 대조표.자산.비유동자산.합계 비유동자산 //
	public BigDecimal getTotalNonCurrentAssets(LocalDateTime eDate) {
		List<Object[]> assetLists = getAssetLists(eDate);

		BigDecimal totalC_Asset = BigDecimal.ZERO;

		if (assetLists != null && !assetLists.isEmpty()) {
			for (Object[] asset : assetLists) {
				totalC_Asset = totalC_Asset.add((BigDecimal) asset[1]);
			}
			return totalC_Asset.subtract(getWriteDown(eDate));
		} else {
			return BigDecimal.ZERO;
		}
	}

	// 대차 대조표.자산.총 자산 //
	public BigDecimal getTotalAssets(LocalDateTime eDate) {
		return getCurrentAssets(eDate).add(getTotalNonCurrentAssets(eDate));
	}

	// 대차 대조표.부채 및 자본.유동부채.단기차입금 //
	public BigDecimal getShortTermDebt(LocalDateTime eDate) {
		LocalDateTime futureDate = eDate.plusYears(1);

		List<String> shortTermDebtNames = liabilityRepository.findNameShortDebtNames(eDate, futureDate);

		BigDecimal shortTermDebt = assetLiabilityLogRepository.findShortTermDebt(shortTermDebtNames, eDate);

		return shortTermDebt != null ? shortTermDebt : BigDecimal.ZERO;
	}

	// 대차 대조표.부채 및 자본.유동부채.합계 유동부채 //
	public BigDecimal getCurrentLiabilities(LocalDateTime eDate) {
		return getShortTermDebt(eDate); // +매입채무 추가필요
	}

	// 대차 대조표.부채 및 자본.비유동부채.장기차입금 //
	public BigDecimal getLongTermDebt(LocalDateTime eDate) {
		LocalDateTime futureDate = eDate.plusYears(1);

		List<String> longTermDebtNames = liabilityRepository.findNameLongDebtNames(futureDate);

		BigDecimal longTermDebt = assetLiabilityLogRepository.findShortTermDebt(longTermDebtNames, eDate);

		return longTermDebt != null ? longTermDebt : BigDecimal.ZERO;
	}

	// 대차 대조표.부채 및 자본.비유동부채.합계 비유동부채 //
	public BigDecimal getNonCurrentLiabilities(LocalDateTime eDate) {
		return getLongTermDebt(eDate);
	}

	// 대차 대조표.부채 및 자본.총 부채 //
	public BigDecimal getTotalLiabilities(LocalDateTime eDate) {
		return getCurrentLiabilities(eDate).add(getNonCurrentLiabilities(eDate));
	}

	// 대차 대조표.부채 및 자본.자본.자본금 //
	// 자본변동표.자본금 //
	public BigDecimal getEquity(LocalDateTime eDate) {
		List<String> equityNames = Arrays.asList("예치금", "현금");

		BigDecimal equity = assetLiabilityLogRepository.findEquity(equityNames, eDate);

		return equity != null ? equity : BigDecimal.ZERO;
	}

	// 대차 대조표.부채 및 자본.자본.이익잉여금 //
	public BigDecimal getRetainedEarnings(LocalDateTime eDate) {
		LocalDateTime firstDate = LocalDateTime.of(2023, 1, 1, 0, 0);

		return getNetProfit(firstDate, eDate);
	}

	// 대차 대조표.부채 및 자본.자본.총 자본 //
	public BigDecimal getTotalCapital(LocalDateTime eDate) {
		return getEquity(eDate).add(getRetainedEarnings(eDate));
	}

	// 대차 대조표.부채 및 자본.총 부채 및 자본 //
	public BigDecimal getTotalLiabilityCapital(LocalDateTime eDate) {
		return getTotalLiabilities(eDate).add(getTotalCapital(eDate));
	}

	// 현금흐름표.투자활동으로 인한 현금흐름 //
	public List<Object[]> getCashFlowFromInvestmentActivities(LocalDateTime sDate, LocalDateTime eDate) {
		List<String> exclusionNames = Arrays.asList("현금", "예치금");
		
		List<Object[]> cashFlowFromInvestmentActivities = assetLiabilityLogRepository.findCashFlow(exclusionNames, sDate,
				eDate);
		
		return cashFlowFromInvestmentActivities ;
	}

	// 현금흐름표.재무활동으로 인한 현금흐름 //
	public BigDecimal getCashFlowFromFinancialActivities(LocalDateTime sDate, LocalDateTime eDate) {
		BigDecimal cashFlowFromFinancialActivities = assetLiabilityLogRepository.findCashFlowFinancial(sDate, eDate);

		return cashFlowFromFinancialActivities != null ? cashFlowFromFinancialActivities : BigDecimal.ZERO;
	}

	// 자본변동표.이익잉여금.기초 이익잉여금 //
	public BigDecimal getBasicRetainedEarnings(LocalDateTime sDate, LocalDateTime eDate) {
		return getRetainedEarnings(eDate).subtract(getNetProfit(sDate, eDate));
	}

	// 자본변동표.이익잉여금.기말 이익잉여금 //
	public BigDecimal getEdRetainedEarnings(LocalDateTime eDate) {
		return getRetainedEarnings(eDate); // -배당금 추가필요
	}

	// 자본변동표.총 자본 //
	public BigDecimal getAllCapital(LocalDateTime sDate, LocalDateTime eDate) {
		return getEquity(eDate).add(getEdRetainedEarnings(eDate));
	}

	
	@Test
	void functionTest() {
	    LocalDateTime[] previousQuarter = getQuartersService.getPreviousQuartersDate();
	    LocalDateTime startDate = LocalDateTime.of(2023, 1, 1, 0, 0);
	    LocalDateTime endDate = previousQuarter[1];

	    // 데이터 가져오기
	    List<Object[]> cashFlowData = getCashFlowFromInvestmentActivities(startDate, endDate);

	    System.out.println("Retrieved Cash Flow Data:");
	    for (Object data : cashFlowData) {
	        if (data instanceof Object[]) {
	            Object[] row = (Object[]) data;
	            System.out.println("Name: " + row[0] + ", Amount: " + row[1]);
	        } else {
	            System.out.println("Unexpected format: " + data);
	        }
	    }
	}

}
