package com.erp.erpsystem.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ChartsResponse {
	private Map<String, List<BigDecimal>> salesAndNetProfit;
	private Map<String, List<BigDecimal>> financialRatios;
	private Map<String, BigDecimal> costSummary;
	private Map<String, BigDecimal> capitalLiabilityRatio;
	private List<BigDecimal> liabilities;
}
