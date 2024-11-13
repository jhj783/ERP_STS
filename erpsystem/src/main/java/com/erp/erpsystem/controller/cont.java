package com.erp.erpsystem.controller;

/*
import com.erp.erpsystem.service.FinancialStatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/financial")
public class FinancialStatementController {

    @Autowired
    private FinancialStatementService financialStatementService;

    // 매출액 가져오기 API
    @GetMapping("/sales")
    public double getSales(@RequestParam String startDate, @RequestParam String endDate) {
        LocalDateTime sDate = LocalDateTime.parse(startDate);
        LocalDateTime eDate = LocalDateTime.parse(endDate);
        return financialStatementService.getSales(sDate, eDate);
    }

    // 원자재 구입 비용 가져오기 API
    @GetMapping("/raw-materials")
    public double getRawMaterials(@RequestParam String startDate, @RequestParam String endDate) {
        LocalDateTime sDate = LocalDateTime.parse(startDate);
        LocalDateTime eDate = LocalDateTime.parse(endDate);
        return financialStatementService.getRawMaterials(sDate, eDate);
    }

    // 순이익 가져오기 API
    @GetMapping("/net-profit")
    public double getNetProfit(@RequestParam String startDate, @RequestParam String endDate) {
        LocalDateTime sDate = LocalDateTime.parse(startDate);
        LocalDateTime eDate = LocalDateTime.parse(endDate);
        return financialStatementService.getNetProfit(sDate, eDate);
    }

    // 감가상각 비용 가져오기 API
    @GetMapping("/depreciation")
    public double getDepreciation(@RequestParam String startDate, @RequestParam String endDate) {
        LocalDateTime sDate = LocalDateTime.parse(startDate);
        LocalDateTime eDate = LocalDateTime.parse(endDate);
        return financialStatementService.getDepreciation(sDate, eDate);
    }

    // 이자 비용 가져오기 API
    @GetMapping("/interest")
    public double getInterest(@RequestParam String startDate, @RequestParam String endDate) {
        LocalDateTime sDate = LocalDateTime.parse(startDate);
        LocalDateTime eDate = LocalDateTime.parse(endDate);
        return financialStatementService.getInterest(sDate, eDate);
    }

    // 현금 가져오기 API
    @GetMapping("/cash")
    public double getCash(@RequestParam String endDate) {
        LocalDateTime eDate = LocalDateTime.parse(endDate);
        return financialStatementService.getCash(eDate);
    }

    // 유동자산 합계 가져오기 API
    @GetMapping("/current-assets")
    public double getCurrentAssets(@RequestParam String endDate) {
        LocalDateTime eDate = LocalDateTime.parse(endDate);
        return financialStatementService.getCurrentAssets(eDate);
    }

    // 비유동자산 목록 가져오기 API
    @GetMapping("/asset-lists")
    public List<Object[]> getAssetLists(@RequestParam String endDate) {
        LocalDateTime eDate = LocalDateTime.parse(endDate);
        return financialStatementService.getAssetLists(eDate);
    }

    // 감가상각 누계액 가져오기 API
    @GetMapping("/write-down")
    public double getWriteDown(@RequestParam String endDate) {
        LocalDateTime eDate = LocalDateTime.parse(endDate);
        return financialStatementService.getWriteDown(eDate);
    }

    // 비유동자산 합계 가져오기 API
    @GetMapping("/total-non-current-assets")
    public double getTotalNonCurrentAssets(@RequestParam String endDate) {
        LocalDateTime eDate = LocalDateTime.parse(endDate);
        return financialStatementService.getTotalNonCurrentAssets(eDate);
    }

    // 총 자산 가져오기 API
    @GetMapping("/total-assets")
    public double getTotalAssets(@RequestParam String endDate) {
        LocalDateTime eDate = LocalDateTime.parse(endDate);
        return financialStatementService.getTotalAssets(eDate);
    }

    // 단기차입금 가져오기 API
    @GetMapping("/short-term-debt")
    public double getShortTermDebt(@RequestParam String endDate) {
        LocalDateTime eDate = LocalDateTime.parse(endDate);
        return financialStatementService.getShortTermDebt(eDate);
    }

    // 유동부채 합계 가져오기 API
    @GetMapping("/current-liabilities")
    public double getCurrentLiabilities(@RequestParam String endDate) {
        LocalDateTime eDate = LocalDateTime.parse(endDate);
        return financialStatementService.getCurrentLiabilities(eDate);
    }

    // 장기차입금 가져오기 API
    @GetMapping("/long-term-debt")
    public double getLongTermDebt(@RequestParam String endDate) {
        LocalDateTime eDate = LocalDateTime.parse(endDate);
        return financialStatementService.getLongTermDebt(eDate);
    }

    // 비유동부채 합계 가져오기 API
    @GetMapping("/non-current-liabilities")
    public double getNonCurrentLiabilities(@RequestParam String endDate) {
        LocalDateTime eDate = LocalDateTime.parse(endDate);
        return financialStatementService.getNonCurrentLiabilities(eDate);
    }

    // 총 부채 가져오기 API
    @GetMapping("/total-liabilities")
    public double getTotalLiabilities(@RequestParam String endDate) {
        LocalDateTime eDate = LocalDateTime.parse(endDate);
        return financialStatementService.getTotalLiabilities(eDate);
    }

    // 자본금 가져오기 API
    @GetMapping("/equity")
    public double getEquity(@RequestParam String endDate) {
        LocalDateTime eDate = LocalDateTime.parse(endDate);
        return financialStatementService.getEquity(eDate);
    }

    // 이익잉여금 가져오기 API
    @GetMapping("/retained-earnings")
    public double getRetainedEarnings(@RequestParam String endDate) {
        LocalDateTime eDate = LocalDateTime.parse(endDate);
        return financialStatementService.getRetainedEarnings(eDate);
    }

    // 총 자본 가져오기 API
    @GetMapping("/total-capital")
    public double getTotalCapital(@RequestParam String endDate) {
        LocalDateTime eDate = LocalDateTime.parse(endDate);
        return financialStatementService.getTotalCapital(eDate);
    }

    // 총 부채 및 자본 가져오기 API
    @GetMapping("/total-liability-capital")
    public double getTotalLiabilityCapital(@RequestParam String endDate) {
        LocalDateTime eDate = LocalDateTime.parse(endDate);
        return financialStatementService.getTotalLiabilityCapital(eDate);
    }

    // 투자활동으로 인한 현금흐름 가져오기 API
    @GetMapping("/cash-flow-investment")
    public double getCashFlowFromInvestmentActivities(@RequestParam String startDate, @RequestParam String endDate) {
        LocalDateTime sDate = LocalDateTime.parse(startDate);
        LocalDateTime eDate = LocalDateTime.parse(endDate);
        return financialStatementService.getCashFlowFromInvestmentActivities(sDate, eDate);
    }

    // 재무활동으로 인한 현금흐름 가져오기 API
    @GetMapping("/cash-flow-financial")
    public double getCashFlowFromFinancialActivities(@RequestParam String startDate, @RequestParam String endDate) {
        LocalDateTime sDate = LocalDateTime.parse(startDate);
        LocalDateTime eDate = LocalDateTime.parse(endDate);
        return financialStatementService.getCashFlowFromFinancialActivities(sDate, eDate);
    }

    // 기초 이익잉여금 가져오기 API
    @GetMapping("/basic-retained-earnings")
    public double getBasicRetainedEarnings(@RequestParam String startDate, @RequestParam String endDate) {
        LocalDateTime sDate = LocalDateTime.parse(startDate);
        LocalDateTime eDate = LocalDateTime.parse(endDate);
        return financialStatementService.getBasicRetainedEarnings(sDate, eDate);
    }

    // 기말 이익잉여금 가져오기 API
    @GetMapping("/ed-retained-earnings")
    public double getEdRetainedEarnings(@RequestParam String endDate) {
        LocalDateTime eDate = LocalDateTime.parse(endDate);
        return financialStatementService.getEdRetainedEarnings(eDate);
    }

    // 총 자본 가져오기 API
    @GetMapping("/all-capital")
    public double getAllCapital(@RequestParam String startDate, @RequestParam String endDate) {
        LocalDateTime sDate = LocalDateTime.parse(startDate);
        LocalDateTime eDate = LocalDateTime.parse(endDate);
        return financialStatementService.getAllCapital(sDate, eDate);
    }
}

// 예시 URL: http://localhost:8080/api/financial/sales?startDate=2023-01-01T00:00&endDate=2023-12-31T23:59
// 이 URL을 호출하면 해당 기간의 매출액이 반환됩니다.
*/