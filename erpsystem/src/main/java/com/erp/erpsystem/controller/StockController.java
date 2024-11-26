package com.erp.erpsystem.controller;

import com.erp.erpsystem.db.Stock;
import com.erp.erpsystem.db.StockRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class StockController {
/*
    private final StockRepository stockRepository;

    public StockController(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @GetMapping("/stock")
    public String getStocks(Model model) {
        // 데이터베이스에서 모든 재고 데이터 가져오기
        List<Stock> stocks = stockRepository.findAll();

        // 모델에 재고 데이터 추가
        model.addAttribute("stocks", stocks);

        // 타임리프 HTML 파일로 이동
        return "stock"; // resources/templates/stocks.html 파일로 매핑
    }
    */
}
