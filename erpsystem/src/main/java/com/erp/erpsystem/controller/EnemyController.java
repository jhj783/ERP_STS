package com.erp.erpsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EnemyController {
    @GetMapping("/main")
    public String showMainPage() {
        return "dashboard";
    }
    
    @GetMapping("/account")
    public String showBalanceLogPage() {
        return "account";
    }
    
    @GetMapping("/asset")
    public String showAssetPage() {
        return "Asset";
    }
    
    @GetMapping("/liability")
    public String showLiabilityPage() {
        return "Liability";
    }
    
    @GetMapping("/stock")
    public String showStockPage() {
        return "Stock";
    }
    
    /*
    @GetMapping("/financialstatements")
    public String showFinancialStatementsPage() {
        return "FinancialStatements";
    }
    */
}
