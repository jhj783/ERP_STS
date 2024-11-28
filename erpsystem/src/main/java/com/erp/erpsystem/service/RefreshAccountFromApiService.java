package com.erp.erpsystem.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.erpsystem.db.Account;
import com.erp.erpsystem.db.AccountRepository;

@Service
public class RefreshAccountFromApiService {

    @Autowired
    private ReceiveApiService receiveApiService;
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private RefreshService refreshService;

    public void saveAccountsFromApi() {
    	
    	int maxTuno = accountRepository.findMaxTuno();
   
        try {
            var apiData = receiveApiService.searchApiData();

            for (int i = 0; i < apiData.length(); i++) {
                JSONObject record = apiData.getJSONObject(i);
                int tuno = Integer.valueOf(record.getString("Tuno"));
                
                // 입금, 출금 구분 필요
                if(maxTuno < tuno) {
                	
		            BigDecimal amount = new BigDecimal(record.getString("Tram")); // 거래 금액
		            
		            String trdd = record.getString("Trdd"); // 예: "20241127"
		            LocalDateTime dateTime = LocalDateTime.parse(trdd + "000000", DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		            
		            String description = record.getString("BnprCntn"); // 거래 내용
		            String type = dealType(record.getString("MnrcDrotDsnc")); // 거래 유형
		            BigDecimal afterBalance = new BigDecimal(record.getString("AftrBlnc")); // 잔액
		
		            // Account 객체 생성
		            Account account = createAccount(amount, dateTime, description, type, afterBalance, tuno);
		
		            this.accountRepository.save(account);
		            
		            // 에셋 테이블의 "예치금" 갱신
		            refreshService.updateAssetByAccount();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String dealType(String deal) {
        if ("1".equals(deal) || "2".equals(deal)) {
            return "수익";
        }
        return "지출";
    }

    private Account createAccount(BigDecimal amount, LocalDateTime date, String description, String type, BigDecimal afterBalance, int tuno) {
        Account account = new Account();
        account.setDate(date);
        account.setDescription(description);
        account.setAmount(amount);
        account.setType(type);
        account.setAfterBalance(afterBalance);
        account.setTuno(tuno);
        return account;
    }
}
