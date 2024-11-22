package com.erp.erpsystem.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.erp.erpsystem.response.FinancialSummaryResponse;
import com.erp.erpsystem.service.FinancialStatementService;

@Controller
public class FsController {

    @Autowired
    private FinancialStatementService financialStatementService;

    @GetMapping("/financialstatements")
    public String getFinancialStatements(Model model) {
        // 필요한 날짜 범위 설정 (예: 2023년 전체)
        LocalDateTime startDate = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 12, 31, 23, 59);

        // DTO 생성 및 데이터 설정
        FinancialSummaryResponse response = FinancialSummaryResponse.builder()
            .sales(financialStatementService.getSales(startDate, endDate))
            .rawMaterials(financialStatementService.getRawMaterials(startDate, endDate))
            .netProfit(financialStatementService.getNetProfit(startDate, endDate))
            .depreciation(financialStatementService.getDepreciation(startDate, endDate))
            .interest(financialStatementService.getInterest(startDate, endDate))
            .cash(financialStatementService.getCash(endDate))
            .currentAssets(financialStatementService.getCurrentAssets(endDate))
            .totalNonCurrentAssets(financialStatementService.getTotalNonCurrentAssets(endDate))
            .totalAssets(financialStatementService.getTotalAssets(endDate))
            .shortTermDebt(financialStatementService.getShortTermDebt(endDate))
            .currentLiabilities(financialStatementService.getCurrentLiabilities(endDate))
            .longTermDebt(financialStatementService.getLongTermDebt(endDate))
            .nonCurrentLiabilities(financialStatementService.getNonCurrentLiabilities(endDate))
            .totalLiabilities(financialStatementService.getTotalLiabilities(endDate))
            .equity(financialStatementService.getEquity(endDate))
            .retainedEarnings(financialStatementService.getRetainedEarnings(endDate))
            .totalCapital(financialStatementService.getTotalCapital(endDate))
            .totalLiabilityCapital(financialStatementService.getTotalLiabilityCapital(endDate))
            .cashFlowFromInvestmentActivities(financialStatementService.getCashFlowFromInvestmentActivities(startDate, endDate))
            .cashFlowFromFinancialActivities(financialStatementService.getCashFlowFromFinancialActivities(startDate, endDate))
            .basicRetainedEarnings(financialStatementService.getBasicRetainedEarnings(startDate, endDate))
            .edRetainedEarnings(financialStatementService.getEdRetainedEarnings(endDate))
            .allCapital(financialStatementService.getAllCapital(startDate, endDate))
            .assetLists(financialStatementService.getAssetLists(endDate))
            .build();

        // 모델에 DTO 추가
        model.addAttribute("financialData", response);

        return "FinancialStatements"; // Thymeleaf 템플릿 이름
    }
}
