package com.erp.erpsystem.response;

import java.util.List;

import com.erp.erpsystem.db.Asset;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AssetResponse {
	private List<Asset> asset; 
}
