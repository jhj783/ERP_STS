package com.erp.erpsystem.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.erp.erpsystem.service.RefreshService;

@Controller
public class AssetController {

    @Autowired
    private RefreshService refreshService;

    @PostMapping("/addAsset")
    public String addAsset(
        @RequestParam("name") String name,
        @RequestParam("amount") BigDecimal amount,
        @RequestParam("acquisitionCost") BigDecimal acquisitionCost,
        @RequestParam("type") String type
    ) {
        refreshService.insertAssetByAsset(name, amount, acquisitionCost, type);

        // 완료 후 자산 목록 페이지로 리다이렉트
        return "redirect:/asset";
    }
    
    @PostMapping("/deleteAsset")
    public String deleteAsset(@RequestParam("name") String name) {
        try {
            // 자산 삭제 서비스 호출
            refreshService.deleteAssetByName(name);
        } catch (IllegalArgumentException e) {
            // 에러 처리 (예: 잘못된 자산 이름)
            System.out.println("Error: " + e.getMessage());
        }

        // 완료 후 자산 목록 페이지로 리다이렉트
        return "redirect:/asset";
    }
}
