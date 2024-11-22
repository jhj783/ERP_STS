// FsController.java

package com.erp.erpsystem.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// 필요한 import 추가
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

        // 숫자 포맷터 생성
        DecimalFormat decimalFormatter = new DecimalFormat("#,##0");

        // 각 필드의 값을 가져옵니다.
        BigDecimal sales = financialStatementService.getSales(startDate, endDate);
        BigDecimal rawMaterials = financialStatementService.getRawMaterials(startDate, endDate);
        BigDecimal netProfit = financialStatementService.getNetProfit(startDate, endDate);
        BigDecimal depreciation = financialStatementService.getDepreciation(startDate, endDate);
        BigDecimal interest = financialStatementService.getInterest(startDate, endDate);
        BigDecimal cash = financialStatementService.getCash(endDate);
        BigDecimal currentAssets = financialStatementService.getCurrentAssets(endDate);
        BigDecimal totalNonCurrentAssets = financialStatementService.getTotalNonCurrentAssets(endDate);
        BigDecimal totalAssets = financialStatementService.getTotalAssets(endDate);
        BigDecimal shortTermDebt = financialStatementService.getShortTermDebt(endDate);
        BigDecimal currentLiabilities = financialStatementService.getCurrentLiabilities(endDate);
        BigDecimal longTermDebt = financialStatementService.getLongTermDebt(endDate);
        BigDecimal nonCurrentLiabilities = financialStatementService.getNonCurrentLiabilities(endDate);
        BigDecimal totalLiabilities = financialStatementService.getTotalLiabilities(endDate);
        BigDecimal equity = financialStatementService.getEquity(endDate);
        BigDecimal retainedEarnings = financialStatementService.getRetainedEarnings(endDate);
        BigDecimal totalCapital = financialStatementService.getTotalCapital(endDate);
        BigDecimal totalLiabilityCapital = financialStatementService.getTotalLiabilityCapital(endDate);
        BigDecimal cashFlowFromInvestmentActivities = financialStatementService.getCashFlowFromInvestmentActivities(startDate, endDate);
        BigDecimal cashFlowFromFinancialActivities = financialStatementService.getCashFlowFromFinancialActivities(startDate, endDate);
        BigDecimal basicRetainedEarnings = financialStatementService.getBasicRetainedEarnings(startDate, endDate);
        BigDecimal edRetainedEarnings = financialStatementService.getEdRetainedEarnings(endDate);
        BigDecimal allCapital = financialStatementService.getAllCapital(startDate, endDate);
        List<Object[]> assetLists = financialStatementService.getAssetLists(endDate);

        // 포맷팅된 문자열 생성
        String formattedSales = formatCurrency(sales, decimalFormatter);
        String formattedRawMaterials = formatCurrency(rawMaterials, decimalFormatter);
        String formattedNetProfit = formatCurrency(netProfit, decimalFormatter);
        String formattedDepreciation = formatCurrency(depreciation, decimalFormatter);
        String formattedInterest = formatCurrency(interest, decimalFormatter);
        String formattedCash = formatCurrency(cash, decimalFormatter);
        String formattedCurrentAssets = formatCurrency(currentAssets, decimalFormatter);
        String formattedTotalNonCurrentAssets = formatCurrency(totalNonCurrentAssets, decimalFormatter);
        String formattedTotalAssets = formatCurrency(totalAssets, decimalFormatter);
        String formattedShortTermDebt = formatCurrency(shortTermDebt, decimalFormatter);
        String formattedCurrentLiabilities = formatCurrency(currentLiabilities, decimalFormatter);
        String formattedLongTermDebt = formatCurrency(longTermDebt, decimalFormatter);
        String formattedNonCurrentLiabilities = formatCurrency(nonCurrentLiabilities, decimalFormatter);
        String formattedTotalLiabilities = formatCurrency(totalLiabilities, decimalFormatter);
        String formattedEquity = formatCurrency(equity, decimalFormatter);
        String formattedRetainedEarnings = formatCurrency(retainedEarnings, decimalFormatter);
        String formattedTotalCapital = formatCurrency(totalCapital, decimalFormatter);
        String formattedTotalLiabilityCapital = formatCurrency(totalLiabilityCapital, decimalFormatter);
        String formattedCashFlowFromInvestmentActivities = formatCurrency(cashFlowFromInvestmentActivities, decimalFormatter);
        String formattedCashFlowFromFinancialActivities = formatCurrency(cashFlowFromFinancialActivities, decimalFormatter);
        String formattedBasicRetainedEarnings = formatCurrency(basicRetainedEarnings, decimalFormatter);
        String formattedEdRetainedEarnings = formatCurrency(edRetainedEarnings, decimalFormatter);
        String formattedAllCapital = formatCurrency(allCapital, decimalFormatter);
        // assetLists는 필요에 따라 처리

        // DTO 생성 및 데이터 설정
        FinancialSummaryResponse response = FinancialSummaryResponse.builder()
                // 원래의 BigDecimal 값 설정
                .sales(sales)
                .rawMaterials(rawMaterials)
                .netProfit(netProfit)
                .depreciation(depreciation)
                .interest(interest)
                .cash(cash)
                .currentAssets(currentAssets)
                .totalNonCurrentAssets(totalNonCurrentAssets)
                .totalAssets(totalAssets)
                .shortTermDebt(shortTermDebt)
                .currentLiabilities(currentLiabilities)
                .longTermDebt(longTermDebt)
                .nonCurrentLiabilities(nonCurrentLiabilities)
                .totalLiabilities(totalLiabilities)
                .equity(equity)
                .retainedEarnings(retainedEarnings)
                .totalCapital(totalCapital)
                .totalLiabilityCapital(totalLiabilityCapital)
                .cashFlowFromInvestmentActivities(cashFlowFromInvestmentActivities)
                .cashFlowFromFinancialActivities(cashFlowFromFinancialActivities)
                .basicRetainedEarnings(basicRetainedEarnings)
                .edRetainedEarnings(edRetainedEarnings)
                .allCapital(allCapital)
                .assetLists(assetLists)
                // 포맷팅된 문자열 설정
                .formattedSales(formattedSales)
                .formattedRawMaterials(formattedRawMaterials)
                .formattedNetProfit(formattedNetProfit)
                .formattedDepreciation(formattedDepreciation)
                .formattedInterest(formattedInterest)
                .formattedCash(formattedCash)
                .formattedCurrentAssets(formattedCurrentAssets)
                .formattedTotalNonCurrentAssets(formattedTotalNonCurrentAssets)
                .formattedTotalAssets(formattedTotalAssets)
                .formattedShortTermDebt(formattedShortTermDebt)
                .formattedCurrentLiabilities(formattedCurrentLiabilities)
                .formattedLongTermDebt(formattedLongTermDebt)
                .formattedNonCurrentLiabilities(formattedNonCurrentLiabilities)
                .formattedTotalLiabilities(formattedTotalLiabilities)
                .formattedEquity(formattedEquity)
                .formattedRetainedEarnings(formattedRetainedEarnings)
                .formattedTotalCapital(formattedTotalCapital)
                .formattedTotalLiabilityCapital(formattedTotalLiabilityCapital)
                .formattedCashFlowFromInvestmentActivities(formattedCashFlowFromInvestmentActivities)
                .formattedCashFlowFromFinancialActivities(formattedCashFlowFromFinancialActivities)
                .formattedBasicRetainedEarnings(formattedBasicRetainedEarnings)
                .formattedEdRetainedEarnings(formattedEdRetainedEarnings)
                .formattedAllCapital(formattedAllCapital)
                .build();

        // 모델에 DTO 추가
        model.addAttribute("financialData", response);

        return "FinancialStatements"; // Thymeleaf 템플릿 이름
    }

    // 포맷팅을 위한 헬퍼 메서드 추가
    private String formatCurrency(BigDecimal value, DecimalFormat formatter) {
        return value != null ? formatter.format(value) + "원" : "금액 없음";
    }
}
