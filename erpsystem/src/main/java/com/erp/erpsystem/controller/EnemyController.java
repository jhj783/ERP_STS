package com.erp.erpsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.erp.erpsystem.db.Stock;
import com.erp.erpsystem.db.StockRepository;

@Controller
public class EnemyController {	
    @Autowired
    private StockRepository stockRepository;
	
    @GetMapping("/main")
    public String showMainPage() {
        return "dashboard";
    }
    
    @GetMapping("/asset")
    public String showAssetPage() {
        return "Asset";
    }
    
    @GetMapping("/asset/insert")
    public String showInsertAssetPage() {
        return "AssetInsert";
    }
    
    @GetMapping("/asset/delete")
    public String showDeleteAssetPage() {
        return "AssetDelete";
    }
    
    @GetMapping("/liability")
    public String showLiabilityPage() {
        return "Liability";
    }

    @GetMapping("/stock")
    public String getStocks(Model model) {
        List<Stock> stocks = stockRepository.findAll();

        model.addAttribute("stocks", stocks);

        return "stock";
    }
    
    
    @GetMapping("/mainn")
    public String testPage() {
        return "mainn";
    }
    
    /*
    @GetMapping("/financialstatements")
    public String showFinancialStatementsPage() {
        return "FinancialStatements";
    }
    */
}
