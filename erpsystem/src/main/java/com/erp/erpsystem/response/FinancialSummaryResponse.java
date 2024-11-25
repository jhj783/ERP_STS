// FinancialSummaryResponse.java

package com.erp.erpsystem.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class FinancialSummaryResponse {
    // 원래의 BigDecimal 타입 필드들
    private BigDecimal sales; // 매출액
    private BigDecimal rawMaterials; // 매출원가
    private BigDecimal cost; // 판매비 및 관리비
    private BigDecimal depreciation; // 감가상각 누계액
    private BigDecimal interest; // 이자비용
    private BigDecimal totalCost; // 합계 비용
    private BigDecimal netProfit; // 순이익
    private BigDecimal cash; // 현금
    private BigDecimal currentAssets; // 유동자산 합계
    private BigDecimal totalNonCurrentAssets; // 비유동자산 합계
    private BigDecimal totalAssets; // 총자산
    private BigDecimal shortTermDebt; // 단기차입금
    private BigDecimal currentLiabilities; // 유동부채 합계
    private BigDecimal longTermDebt; // 장기차입금
    private BigDecimal nonCurrentLiabilities; // 비유동부채 합계
    private BigDecimal totalLiabilities; // 총부채
    private BigDecimal equity; // 자본금
    private BigDecimal retainedEarnings; // 이익잉여금
    private BigDecimal totalCapital; // 총자본
    private BigDecimal totalLiabilityCapital; // 총부채 및 자본
    private BigDecimal cashFlowFromFinancialActivities; // 재무활동으로 인한 현금흐름
    private BigDecimal basicRetainedEarnings; // 기초 이익잉여금
    private BigDecimal edRetainedEarnings; // 기말 이익잉여금
    private BigDecimal allCapital; // 총 자본
    private BigDecimal writeDown; // 감가상각
    private BigDecimal totalSalesActivityFlow; //합계 영업활동 현금흐름
    private BigDecimal totalInvestmentActivityCashFlow; // 합계 투자활동 현금흐름
    private List<Object[]> assetLists; // 비유동자산 목록
    private List<Object[]> cashFlowFromInvestmentActivities; // 투자활동으로 인한 현금흐름
    private BigDecimal basicEquity; // 기초 자본금
    private BigDecimal basicCapital; // 기초 총 자본

    // 포맷팅된 문자열 필드들
    private String formattedSales; // 포맷팅된 매출액 (예: "1,000,000원")
    private String formattedRawMaterials; // 포맷팅된 매출원가
    private String formattedCost; // 포맷팅된 판매비 및 관리비
    private String formattedDepreciation; // 포맷팅된 감가상각 누계액
    private String formattedInterest; // 포맷팅된 이자비용
    private String formattedTotalCost; // 포맷팅된 합계 비용
    private String formattedNetProfit; // 포맷팅된 순이익
    private String formattedCash; // 포맷팅된 현금
    private String formattedCurrentAssets; // 포맷팅된 유동자산 합계
    private String formattedTotalNonCurrentAssets; // 포맷팅된 비유동자산 합계
    private String formattedTotalAssets; // 포맷팅된 총자산
    private String formattedShortTermDebt; // 포맷팅된 단기차입금
    private String formattedCurrentLiabilities; // 포맷팅된 유동부채 합계
    private String formattedLongTermDebt; // 포맷팅된 장기차입금
    private String formattedNonCurrentLiabilities; // 포맷팅된 비유동부채 합계
    private String formattedTotalLiabilities; // 포맷팅된 총부채
    private String formattedEquity; // 포맷팅된 자본금
    private String formattedRetainedEarnings; // 포맷팅된 이익잉여금
    private String formattedTotalCapital; // 포맷팅된 총자본
    private String formattedTotalLiabilityCapital; // 포맷팅된 총부채 및 자본
    private String formattedCashFlowFromInvestmentActivities; // 포맷팅된 투자활동 현금흐름
    private String formattedCashFlowFromFinancialActivities; // 포맷팅된 재무활동 현금흐름
    private String formattedBasicRetainedEarnings; // 포맷팅된 기초 이익잉여금
    private String formattedEdRetainedEarnings; // 포맷팅된 기말 이익잉여금
    private String formattedAllCapital; // 포맷팅된 총 자본
    private String formattedWriteDown; // 포맷팅된 감가상각
    private String formattedTotalSalesActivityFlow; // 포맷팅된 합계 영업활동 현금흐름
    private String formattedTotalInvestmentActivityCashFlow; //포맷팅된 합계 투자활동 현금흐름
    private String formattedBasicEquity; // 포맷팅된 기초 자본금
    private String formattedBasicCapital; // 포맷팅된 기초 총 자본
    
}
