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
        return stockRepository.findAllNames();
    }
	
	@Transactional
    public void updateStock(String name, int addQuantity) {
        Stock stock = stockRepository.findByName(name)
            .orElseThrow(() -> new IllegalArgumentException("Stock with name " + name + " not found."));
        
        stock.setQuantity(stock.getQuantity() + addQuantity);

        stockRepository.save(stock);
    }
	
}