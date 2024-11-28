package com.erp.erpsystem.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.erp.erpsystem.response.FinancialSummaryResponse;
import com.erp.erpsystem.service.FinancialStatementService;

@Controller
public class FsController {

    @Autowired
    private FinancialStatementService financialStatementService;

    @GetMapping("/financialstatements")
    public String getFinancialStatements(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Model model) {

        // 날짜가 입력되지 않은 경우 기본값 설정 (예: 올해 전체 기간)
        if (startDate == null) {
            startDate = LocalDate.of(2023, 1, 1);
        }
        if (endDate == null) {
            endDate = LocalDate.of(2023, 12, 31);
        }

        // 변수 이름 변경: startDateTime과 endDateTime 사용
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        // 숫자 포맷터 생성
        DecimalFormat decimalFormatter = new DecimalFormat("#,##0");

        // 각 필드의 값을 가져옵니다.
        BigDecimal sales = financialStatementService.getSales(startDateTime, endDateTime);
        BigDecimal rawMaterials = financialStatementService.getRawMaterials(startDateTime, endDateTime);
        BigDecimal cost = financialStatementService.getCost(startDateTime, endDateTime); // 판매비 및 관리비
        BigDecimal depreciation = financialStatementService.getDepreciation(startDateTime, endDateTime); // 감가상각 누계액
        BigDecimal interest = financialStatementService.getInterest(startDateTime, endDateTime);
        BigDecimal totalCost = financialStatementService.getTotalCost(startDateTime, endDateTime);
        BigDecimal netProfit = financialStatementService.getNetProfit(startDateTime, endDateTime);

        BigDecimal cash = financialStatementService.getCash(endDateTime);
        BigDecimal currentAssets = financialStatementService.getCurrentAssets(endDateTime);
        BigDecimal totalNonCurrentAssets = financialStatementService.getTotalNonCurrentAssets(endDateTime);
        BigDecimal totalAssets = financialStatementService.getTotalAssets(endDateTime);

        BigDecimal shortTermDebt = financialStatementService.getShortTermDebt(endDateTime);
        BigDecimal currentLiabilities = financialStatementService.getCurrentLiabilities(endDateTime);
        BigDecimal longTermDebt = financialStatementService.getLongTermDebt(endDateTime);
        BigDecimal nonCurrentLiabilities = financialStatementService.getNonCurrentLiabilities(endDateTime);
        BigDecimal totalLiabilities = financialStatementService.getTotalLiabilities(endDateTime);

        BigDecimal equity = financialStatementService.getEquity(endDateTime);
        BigDecimal retainedEarnings = financialStatementService.getRetainedEarnings(endDateTime);
        BigDecimal totalCapital = financialStatementService.getTotalCapital(endDateTime);
        BigDecimal totalLiabilityCapital = financialStatementService.getTotalLiabilityCapital(endDateTime);

        BigDecimal cashFlowFromFinancialActivities = financialStatementService
                .getCashFlowFromFinancialActivities(startDateTime, endDateTime);

        BigDecimal basicRetainedEarnings = financialStatementService.getBasicRetainedEarnings(startDateTime, endDateTime);
        BigDecimal edRetainedEarnings = financialStatementService.getEdRetainedEarnings(endDateTime);
        BigDecimal allCapital = financialStatementService.getAllCapital(endDateTime);

        BigDecimal writeDown = financialStatementService.getWriteDown(endDateTime);
        BigDecimal totalSalesActivityFlow = financialStatementService.getTotalSalesActivityFlow(startDateTime, endDateTime);
        BigDecimal totalInvestmentActivityCashFlow = financialStatementService
                .getTotalInvestmentActivityCashFlow(startDateTime, endDateTime);
        List<Object[]> assetLists = financialStatementService.getAssetLists(endDateTime);
        List<Object[]> cashFlowFromInvestmentActivities = financialStatementService
                .getCashFlowFromInvestmentActivities(startDateTime, endDateTime);

        BigDecimal basicEquity = financialStatementService.getBasicEquity(startDateTime);
        BigDecimal basicCapital = financialStatementService.getBasicCapital(startDateTime);
        BigDecimal stockAsset = financialStatementService.getStockAsset();
        BigDecimal purchaseDebt = financialStatementService.getPurchaseDebt(startDateTime, endDateTime);

        // 포맷팅된 문자열 생성
        String formattedSales = formatCurrency(sales, decimalFormatter);
        String formattedRawMaterials = formatCurrency(rawMaterials, decimalFormatter);
        String formattedCost = formatCurrency(cost, decimalFormatter); // 포맷팅된 판매비 및 관리비
        String formattedDepreciation = formatCurrency(depreciation, decimalFormatter); // 포맷팅된 감가상각 누계액
        String formattedInterest = formatCurrency(interest, decimalFormatter);
        String formattedTotalCost = formatCurrency(totalCost, decimalFormatter);
        String formattedNetProfit = formatCurrency(netProfit, decimalFormatter);

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

        String formattedCashFlowFromFinancialActivities = formatCurrency(cashFlowFromFinancialActivities,
                decimalFormatter);

        String formattedBasicRetainedEarnings = formatCurrency(basicRetainedEarnings, decimalFormatter);
        String formattedEdRetainedEarnings = formatCurrency(edRetainedEarnings, decimalFormatter);
        String formattedAllCapital = formatCurrency(allCapital, decimalFormatter);

        String formattedWriteDown = formatCurrency(writeDown, decimalFormatter);
        String formattedTotalSalesActivityFlow = formatCurrency(totalSalesActivityFlow, decimalFormatter);
        String formattedTotalInvestmentActivityCashFlow = formatCurrency(totalInvestmentActivityCashFlow,
                decimalFormatter);

        String formattedBasicEquity = formatCurrency(basicEquity, decimalFormatter);
        String formattedBasicCapital = formatCurrency(basicCapital, decimalFormatter);
        String formattedStockAsset = formatCurrency(stockAsset, decimalFormatter);
        String formattedPurchaseDebt = formatCurrency(purchaseDebt, decimalFormatter);

        // DTO 생성 및 데이터 설정
        FinancialSummaryResponse response = FinancialSummaryResponse.builder()
                // 원래의 BigDecimal 값 설정
                .sales(sales).rawMaterials(rawMaterials).cost(cost) // 판매비 및 관리비 추가
                .depreciation(depreciation) // 감가상각 누계액 추가
                .interest(interest).totalCost(totalCost).netProfit(netProfit).cash(cash).currentAssets(currentAssets)
                .totalNonCurrentAssets(totalNonCurrentAssets).totalAssets(totalAssets).shortTermDebt(shortTermDebt)
                .currentLiabilities(currentLiabilities).longTermDebt(longTermDebt)
                .nonCurrentLiabilities(nonCurrentLiabilities).totalLiabilities(totalLiabilities).equity(equity)
                .retainedEarnings(retainedEarnings).totalCapital(totalCapital)
                .totalLiabilityCapital(totalLiabilityCapital)
                .cashFlowFromInvestmentActivities(cashFlowFromInvestmentActivities)
                .cashFlowFromFinancialActivities(cashFlowFromFinancialActivities)
                .basicRetainedEarnings(basicRetainedEarnings).edRetainedEarnings(edRetainedEarnings)
                .allCapital(allCapital).writeDown(writeDown).assetLists(assetLists)
                .totalSalesActivityFlow(totalSalesActivityFlow)
                .totalInvestmentActivityCashFlow(totalInvestmentActivityCashFlow).basicEquity(basicEquity)
                .basicCapital(basicCapital).stockAsset(stockAsset).purchaseDebt(purchaseDebt)
                // 포맷팅된 문자열 설정
                .formattedSales(formattedSales).formattedRawMaterials(formattedRawMaterials)
                .formattedCost(formattedCost) // 포맷팅된 판매비 및 관리비 추가
                .formattedDepreciation(formattedDepreciation) // 포맷팅된 감가상각 누계액 추가
                .formattedInterest(formattedInterest).formattedTotalCost(formattedTotalCost)
                .formattedNetProfit(formattedNetProfit).formattedCash(formattedCash)
                .formattedCurrentAssets(formattedCurrentAssets)
                .formattedTotalNonCurrentAssets(formattedTotalNonCurrentAssets)
                .formattedTotalAssets(formattedTotalAssets).formattedShortTermDebt(formattedShortTermDebt)
                .formattedCurrentLiabilities(formattedCurrentLiabilities).formattedLongTermDebt(formattedLongTermDebt)
                .formattedNonCurrentLiabilities(formattedNonCurrentLiabilities)
                .formattedTotalLiabilities(formattedTotalLiabilities).formattedEquity(formattedEquity)
                .formattedRetainedEarnings(formattedRetainedEarnings).formattedTotalCapital(formattedTotalCapital)
                .formattedTotalLiabilityCapital(formattedTotalLiabilityCapital)
                .formattedCashFlowFromFinancialActivities(formattedCashFlowFromFinancialActivities)
                .formattedBasicRetainedEarnings(formattedBasicRetainedEarnings)
                .formattedEdRetainedEarnings(formattedEdRetainedEarnings).formattedAllCapital(formattedAllCapital)
                .formattedWriteDown(formattedWriteDown).formattedTotalSalesActivityFlow(formattedTotalSalesActivityFlow)
                .formattedTotalInvestmentActivityCashFlow(formattedTotalInvestmentActivityCashFlow)
                .formattedBasicEquity(formattedBasicEquity).formattedBasicCapital(formattedBasicCapital)
                .formattedStockAsset(formattedStockAsset).formattedPurchaseDebt(formattedPurchaseDebt).build();

        // 모델에 DTO 추가
        model.addAttribute("financialData", response);
        
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "FinancialStatements"; // Thymeleaf 템플릿 이름
    }

    // 포맷팅을 위한 헬퍼 메서드 추가
    private String formatCurrency(BigDecimal value, DecimalFormat formatter) {
        return value != null ? formatter.format(value) + "원" : "금액 없음";
    }
}
