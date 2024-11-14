package com.erp.erpsystem.controller;

import com.erp.erpsystem.response.FinancialSummaryResponse;
import com.erp.erpsystem.service.FinancialStatementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/fs")
public class FinancialStatementController {

    @Autowired
    private FinancialStatementService financialStatementService;

    // 여러 재무 데이터를 한꺼번에 가져오는 API
    @GetMapping("/summary")
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
}
