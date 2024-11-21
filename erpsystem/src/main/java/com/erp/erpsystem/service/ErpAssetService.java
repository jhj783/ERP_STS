package com.erp.erpsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.erpsystem.db.Asset;
import com.erp.erpsystem.db.AssetRepository;

@Service
public class ErpAssetService {
    @Autowired
    private AssetRepository assetRepository;
    
    public List<Asset> getAsset() {
        return assetRepository.findAssetData();
    }
}
