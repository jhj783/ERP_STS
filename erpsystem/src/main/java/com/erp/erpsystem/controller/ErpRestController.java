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

    @GetMapping("/asset")
    public List<Asset> getAllAssets() {
        return erpAssetService.getAsset();
    }

    @GetMapping("/liability")
    public List<Liability> getAllLiabilities() {
        return erpLiabilityService.getLiability();
    }
}
