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
        List<String> stockNames = stockService.getAllStockNames(); // 모든 재고 이름만 가져옵니다.
        model.addAttribute("stockNames", stockNames); // 뷰에 전달
        return "StockManage"; // StockManage.html로 이동
    }

    // 입고/출고 처리
    @PostMapping("/update-stock")
    public String updateStock(
        @RequestParam("name") String name, 
        @RequestParam("quantity") int quantity, 
        @RequestParam("transactionType") String transactionType
    ) {
        // 입고인 경우 양의 값, 출고인 경우 음의 값으로 처리
        if ("IN".equals(transactionType)) {
            stockService.updateStock(name, quantity); // 입고 처리
        } else if ("OUT".equals(transactionType)) {
            stockService.updateStock(name, -quantity); // 출고 처리 (음수값 전달)
        }
        return "redirect:/stock/manage";
    }

}
