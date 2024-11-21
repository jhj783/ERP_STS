package com.erp.erpsystem.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ChartsResponse {
    private Map<String, List<Double>> salesAndNetProfit;
    private Map<String, List<Double>> financialRatios;
    private Map<String, Double> costSummary;
    private Map<String, Double> capitalLiabilityRatio;
    private List<Double> liabilities;
}
