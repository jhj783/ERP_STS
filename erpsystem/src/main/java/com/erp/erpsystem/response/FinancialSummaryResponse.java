package com.erp.erpsystem.response;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
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
    
    private List<Object[]> assetLists;
    
    /*
    @Override
    public String toString() {
        return "FinancialSummaryResponse{" +
                "sales=" + sales +
                ", rawMaterials=" + rawMaterials +
                ", netProfit=" + netProfit +
                ", depreciation=" + depreciation +
                ", interest=" + interest +
                ", cash=" + cash +
                ", currentAssets=" + currentAssets +
                ", totalNonCurrentAssets=" + totalNonCurrentAssets +
                ", totalAssets=" + totalAssets +
                ", shortTermDebt=" + shortTermDebt +
                ", currentLiabilities=" + currentLiabilities +
                ", longTermDebt=" + longTermDebt +
                ", nonCurrentLiabilities=" + nonCurrentLiabilities +
                ", totalLiabilities=" + totalLiabilities +
                ", equity=" + equity +
                ", retainedEarnings=" + retainedEarnings +
                ", totalCapital=" + totalCapital +
                ", totalLiabilityCapital=" + totalLiabilityCapital +
                ", cashFlowFromInvestmentActivities=" + cashFlowFromInvestmentActivities +
                ", cashFlowFromFinancialActivities=" + cashFlowFromFinancialActivities +
                ", basicRetainedEarnings=" + basicRetainedEarnings +
                ", edRetainedEarnings=" + edRetainedEarnings +
                ", allCapital=" + allCapital +
                ", assetLists=" + assetLists +
                '}';
    }
    */
}


