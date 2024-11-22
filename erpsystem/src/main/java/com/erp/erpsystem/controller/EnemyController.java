package com.erp.erpsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EnemyController {

    @GetMapping("/charts")
    public String showChartsPage() {
        return "Charts";
    }
    
    @GetMapping("/testc")
    public String showTestPage() {
        return "testCharts";
    }
    
    @GetMapping("/main")
    public String showMainPage() {
        return "dashboard"; // Dashboard.html 템플릿을 반환
    }
    
    @GetMapping("/account")
    public String showBalanceLogPage() {
        return "account"; // BalanceLog.html 템플릿을 반환
    }
    
    @GetMapping("/asset")
    public String showAssetPage() {
        return "Asset"; // Asset.html 템플릿을 반환
    }
    
    @GetMapping("/liability")
    public String showLiabilityPage() {
        return "Liability"; // Liability.html 템플릿을 반환
    }
    
    @GetMapping("/stock")
    public String showStockPage() {
        return "Stock"; // Stock.html 템플릿을 반환
    }
    
    @GetMapping("/financialstatements")
    public String showFinancialStatementsPage() {
        return "FinancialStatements"; // FinancialStatements.html 템플릿을 반환
    }
    
    @GetMapping("/al")
    public String showAssetLiability() {
    	return "AssetLiability";
    }
}
