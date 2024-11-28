package com.erp.erpsystem.service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;


@Service
public class GetQuartersService {
	// 현재 날짜로부터 전 분기(4개)
	public List<LocalDateTime[]> getPreviousQuartersDates() {
	    List<LocalDateTime[]> previousQuarters = new ArrayList<>();
	    LocalDateTime currentDate = LocalDateTime.now();
	    
	    for (int i = 0; i <= 3; i++) {
	        LocalDateTime[] quarterDates = calculateQuarterDates(currentDate.minusMonths(i * 3));
	        previousQuarters.add(quarterDates);
	    }
	    
	    Collections.reverse(previousQuarters);
	    
	    return previousQuarters;
	}
	
	// 현재 날짜로부터 전 분기(4개) (이름)
	public List<String> getPreviousQuarterNames() {
	    List<String> quarterNames = new ArrayList<>();
	    LocalDateTime currentDate = LocalDateTime.now();

	    for (int i = 0; i < 4; i++) {
	        LocalDateTime referenceDate = currentDate.minusMonths(i * 3); // 현재 날짜로부터 이전 분기 계산
	        String quarterName = getQuarterName(referenceDate); // 분기 이름 계산
	        quarterNames.add(quarterName);
	    } 
	    
	    Collections.reverse(quarterNames);

	    return quarterNames;
	}
	
	// 현재 날짜로부터 전 분기
	public LocalDateTime[] getPreviousQuartersDate() {
		return calculateQuarterDates(LocalDateTime.now());
	}
	
	private LocalDateTime[] calculateQuarterDates(LocalDateTime referenceDate) {
	    int year = referenceDate.getYear();
	    Month startMonth;
	    Month endMonth;
	    
	    if (referenceDate.getMonthValue() <= 3) {
	        startMonth = Month.OCTOBER;
	        endMonth = Month.DECEMBER;
	        year -= 1;
	    } else if (referenceDate.getMonthValue() <= 6) {
	        startMonth = Month.JANUARY;
	        endMonth = Month.MARCH;
	    } else if (referenceDate.getMonthValue() <= 9) {
	        startMonth = Month.APRIL;
	        endMonth = Month.JUNE;
	    } else {
	        startMonth = Month.JULY;
	        endMonth = Month.SEPTEMBER;
	    }
	    
	    LocalDateTime startDate = LocalDateTime.of(year, startMonth, 1, 0, 0);
	    LocalDateTime endDate = LocalDateTime.of(year, endMonth, endMonth.length(referenceDate.toLocalDate().isLeapYear()), 23, 59);
	    
	    return new LocalDateTime[]{startDate, endDate};
	}
	
	// 분기 이름 반환 (연도 뒤로 배치)
	public String getQuarterName(LocalDateTime referenceDate) {
	    int year = referenceDate.getYear();
	    String quarterName;

	    if (referenceDate.getMonthValue() <= 3) {
	        quarterName = (year - 1) + " Q4"; // 작년 4분기
	    } else if (referenceDate.getMonthValue() <= 6) {
	        quarterName = year + " Q1"; // 올해 1분기
	    } else if (referenceDate.getMonthValue() <= 9) {
	        quarterName = year + " Q2"; // 올해 2분기
	    } else {
	        quarterName = year + " Q3"; // 올해 3분기
	    }

	    return quarterName;
	}

	
	/*
	@Test
	void functionTest() {
		for (LocalDateTime[] Dates: getPreviousQuartersDates()){
			System.out.println();
			System.out.println(Dates[0]);
			System.out.println(Dates[1]);
			System.out.println();
			}
	}
	*/
	
	
}
