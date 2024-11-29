package com.erp.erpsystem.controller;

import com.erp.erpsystem.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class StockController {

    @Autowired
    private StockService stockService;

    // 재고 관리 페이지
    @GetMapping("/stock/manage")
    public String manageStock(Model model) {
        List<String> stockNames = stockService.getAllStockNames();
        model.addAttribute("stockNames", stockNames); 
        return "StockManage";
    }

    // 입고/출고 처리
    @PostMapping("/update-stock")
    public String updateStock(
        @RequestParam("name") String name, 
        @RequestParam("quantity") int quantity, 
        @RequestParam("transactionType") String transactionType
    ) {
        if ("IN".equals(transactionType)) {
            stockService.updateStock(name, quantity); 
        } else if ("OUT".equals(transactionType)) {
            stockService.updateStock(name, -quantity); 
        }
        return "redirect:/stock/manage";
    }

}
