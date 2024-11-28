package com.erp.erpsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.erpsystem.db.Stock;
import com.erp.erpsystem.db.StockRepository;

import jakarta.transaction.Transactional;

@Service
public class StockService {

	@Autowired
	StockRepository stockRepository;
	
    public List<String> getAllStockNames() {
        return stockRepository.findAllNames(); // 이름만 조회
    }
	
	@Transactional
    public void updateStock(String name, int addQuantity) {
        // findByName을 호출하고, Optional을 처리
        Stock stock = stockRepository.findByName(name)
            .orElseThrow(() -> new IllegalArgumentException("Stock with name " + name + " not found."));
        
        // 재고 수량 업데이트
        stock.setQuantity(stock.getQuantity() + addQuantity);

        // 재고 저장
        stockRepository.save(stock);
    }
	
}