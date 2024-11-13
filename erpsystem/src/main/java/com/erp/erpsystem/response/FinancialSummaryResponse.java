package com.erp.erpsystem.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinancialSummaryResponse {
    private double sales;
    private double rawMaterials;
    private double netProfit;
    private double depreciation;
    private double interest;
    private double cash;
    private double currentAssets;
    private double totalNonCurrentAssets;
    private double totalAssets;
    private double shortTermDebt;
    private double currentLiabilities;
    private double longTermDebt;
    private double nonCurrentLiabilities;
    private double totalLiabilities;
    private double equity;
    private double retainedEarnings;
    private double totalCapital;
    private double totalLiabilityCapital;
    private double cashFlowFromInvestmentActivities;
    private double cashFlowFromFinancialActivities;
    private double basicRetainedEarnings;
    private double edRetainedEarnings;
    private double allCapital;
}
