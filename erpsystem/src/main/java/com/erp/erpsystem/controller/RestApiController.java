package com.erp.erpsystem.controller;

import com.erp.erpsystem.response.ChartsResponse;
import com.erp.erpsystem.response.FinancialSummaryResponse;
import com.erp.erpsystem.service.FinancialStatementService;
import com.erp.erpsystem.service.ErpChartsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/")
public class RestApiController {
	
    @Autowired
    private FinancialStatementService financialStatementService;
    
    @Autowired
    private ErpChartsService erpChartsService;

    // 재무제표 API
    @GetMapping("/fs")
    public FinancialSummaryResponse getFinancialSummary(
            @RequestParam(name = "startDate") String startDate, 
            @RequestParam(name = "endDate") String endDate) {
        LocalDateTime sDate = LocalDateTime.parse(startDate);
        LocalDateTime eDate = LocalDateTime.parse(endDate);

        FinancialSummaryResponse summary = new FinancialSummaryResponse();
        summary.setSales(financialStatementService.getSales(sDate, eDate));
        summary.setRawMaterials(financialStatementService.getRawMaterials(sDate, eDate));
        summary.setNetProfit(financialStatementService.getNetProfit(sDate, eDate));
        summary.setDepreciation(financialStatementService.getDepreciation(sDate, eDate));
        summary.setInterest(financialStatementService.getInterest(sDate, eDate));
        summary.setCash(financialStatementService.getCash(eDate));
        summary.setCurrentAssets(financialStatementService.getCurrentAssets(eDate));
        summary.setTotalNonCurrentAssets(financialStatementService.getTotalNonCurrentAssets(eDate));
        summary.setTotalAssets(financialStatementService.getTotalAssets(eDate));
        summary.setShortTermDebt(financialStatementService.getShortTermDebt(eDate));
        summary.setCurrentLiabilities(financialStatementService.getCurrentLiabilities(eDate));
        summary.setLongTermDebt(financialStatementService.getLongTermDebt(eDate));
        summary.setNonCurrentLiabilities(financialStatementService.getNonCurrentLiabilities(eDate));
        summary.setTotalLiabilities(financialStatementService.getTotalLiabilities(eDate));
        summary.setEquity(financialStatementService.getEquity(eDate));
        summary.setRetainedEarnings(financialStatementService.getRetainedEarnings(eDate));
        summary.setTotalCapital(financialStatementService.getTotalCapital(eDate));
        summary.setTotalLiabilityCapital(financialStatementService.getTotalLiabilityCapital(eDate));
        summary.setCashFlowFromInvestmentActivities(financialStatementService.getCashFlowFromInvestmentActivities(sDate, eDate));
        summary.setCashFlowFromFinancialActivities(financialStatementService.getCashFlowFromFinancialActivities(sDate, eDate));
        summary.setBasicRetainedEarnings(financialStatementService.getBasicRetainedEarnings(sDate, eDate));
        summary.setEdRetainedEarnings(financialStatementService.getEdRetainedEarnings(eDate));
        summary.setAllCapital(financialStatementService.getAllCapital(sDate, eDate));

        return summary;
    }
    
	// 차트 API
    @GetMapping("/charts")
    public ChartsResponse getChartsData(
            @RequestParam(name = "startDate") String startDate, 
            @RequestParam(name = "endDate") String endDate) {
        LocalDateTime sDate = LocalDateTime.parse(startDate);
        LocalDateTime eDate = LocalDateTime.parse(endDate);

        ChartsResponse chartsResponse = new ChartsResponse();
        chartsResponse.setSalesAndNetProfit(erpChartsService.getSalesAndNetProfit(sDate, eDate));
        chartsResponse.setFinancialRatios(erpChartsService.getFinancialRatios(sDate, eDate));
        chartsResponse.setCostSummary(erpChartsService.getCostSummary(sDate, eDate));
        chartsResponse.setCapitalLiabilityRatio(erpChartsService.getCapitalLiabilityRatio(sDate, eDate));
        chartsResponse.setLiabilities(erpChartsService.getLiabilities(sDate, eDate));

        return chartsResponse;
    }
    
}
