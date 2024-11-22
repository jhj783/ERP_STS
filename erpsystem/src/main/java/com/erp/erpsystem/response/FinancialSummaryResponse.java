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
    private BigDecimal sales;
    private BigDecimal rawMaterials;
    private BigDecimal netProfit;
    private BigDecimal depreciation;
    private BigDecimal interest;
    private BigDecimal cash;
    private BigDecimal currentAssets;
    private BigDecimal totalNonCurrentAssets;
    private BigDecimal totalAssets;
    private BigDecimal shortTermDebt;
    private BigDecimal currentLiabilities;
    private BigDecimal longTermDebt;
    private BigDecimal nonCurrentLiabilities;
    private BigDecimal totalLiabilities;
    private BigDecimal equity;
    private BigDecimal retainedEarnings;
    private BigDecimal totalCapital;
    private BigDecimal totalLiabilityCapital;
    private BigDecimal cashFlowFromInvestmentActivities;
    private BigDecimal cashFlowFromFinancialActivities;
    private BigDecimal basicRetainedEarnings;
    private BigDecimal edRetainedEarnings;
    private BigDecimal allCapital;
    private List<Object[]> assetLists;

    // 포맷팅된 문자열 필드들
    private String formattedSales;
    private String formattedRawMaterials;
    private String formattedNetProfit;
    private String formattedDepreciation;
    private String formattedInterest;
    private String formattedCash;
    private String formattedCurrentAssets;
    private String formattedTotalNonCurrentAssets;
    private String formattedTotalAssets;
    private String formattedShortTermDebt;
    private String formattedCurrentLiabilities;
    private String formattedLongTermDebt;
    private String formattedNonCurrentLiabilities;
    private String formattedTotalLiabilities;
    private String formattedEquity;
    private String formattedRetainedEarnings;
    private String formattedTotalCapital;
    private String formattedTotalLiabilityCapital;
    private String formattedCashFlowFromInvestmentActivities;
    private String formattedCashFlowFromFinancialActivities;
    private String formattedBasicRetainedEarnings;
    private String formattedEdRetainedEarnings;
    private String formattedAllCapital;

    // assetLists는 필요에 따라 포맷팅하거나 그대로 사용하시면 됩니다.
}
