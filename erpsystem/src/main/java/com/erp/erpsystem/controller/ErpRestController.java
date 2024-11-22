package com.erp.erpsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erp.erpsystem.db.Asset;
import com.erp.erpsystem.db.Liability;
import com.erp.erpsystem.service.ErpAssetService;
import com.erp.erpsystem.service.ErpLiabilityService;

@RestController
@RequestMapping("/api/rest")
public class ErpRestController {

    @Autowired
    private ErpAssetService erpAssetService;

    @Autowired
    private ErpLiabilityService erpLiabilityService;

    // GET 요청으로 모든 자산 데이터 조회
    @GetMapping("/asset")
    public List<Asset> getAllAssets() {
        return erpAssetService.getAsset();
    }

    // GET 요청으로 모든 부채 데이터 조회
    @GetMapping("/liability")
    public List<Liability> getAllLiabilities() {
        return erpLiabilityService.getLiability();
    }
}
