package com.erp.erpsystem;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;

import com.erp.erpsystem.db.AccountRepository;
import com.google.gson.Gson;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//@Service
@SpringBootTest
public class ERP_ChartsService {
	
	@Autowired
	private AccountRepository AccountRepository;
	
	//차트.운영비용 //
	public String getCostSummary(LocalDateTime sDate, LocalDateTime eDate) {
	    List<String> descriptions = List.of("복리후생비", "물류비", "공과금", "임대료", "소모품비", "관리비");
	    
	    List<Object[]> results = AccountRepository.findAccountSummaries(descriptions, sDate, eDate);
	    Map<String, Double> costSummaryMap = new HashMap<>();

	    for (Object[] result : results) {
	        String description = (String) result[0];
	        Double amount = (Double) result[1];
	        costSummaryMap.put(description, amount);
	    }

	    // JSON 변환
	    Gson gson = new Gson(); // Gson 라이브러리 사용
	    return gson.toJson(costSummaryMap);
	}
	 
	@Test
	void functionTest() {
		LocalDateTime startDate = LocalDateTime.of(2023, 10, 1, 0, 0);
		LocalDateTime endDate = LocalDateTime.of(2023, 12, 31, 23, 59);

		System.out.println();
		System.out.println(getCostSummary(startDate, endDate));
		System.out.println();
	}
}
