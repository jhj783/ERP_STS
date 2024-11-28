package com.erp.erpsystem.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.erpsystem.db.Liability;
import com.erp.erpsystem.db.LiabilityRepository;

//@SpringBootTest
@Service
public class ErpLiabilityService {
	@Autowired
	private LiabilityRepository liabilityRepository;
	
	@Autowired
	private SendMoneyApiService sendMoneyApiService;
	
	@Autowired
	private RefreshService refreshService;
	
	public List<Liability> getLiability() {
        return liabilityRepository.findLiabilityData();
	}
		
	public void debtRepayment(String name, String amount, boolean isFullRepayment) {
		sendMoneyApiService.sendMoneyApi(name, amount); // api출금 > 계좌테이블 > 에셋테이블(예치금)
		
		LocalDateTime date = LocalDateTime.now();
		BigDecimal repaymentAmount = new BigDecimal(amount);

		if (isFullRepayment) {
			Liability liability = liabilityRepository.findByName(name);
			
			if (liability != null) {
				liabilityRepository.delete(liability);
			}
		} else {
			Liability liability = liabilityRepository.findByName(name);
			if (liability != null) {
				liability.setCurrentValue(liability.getCurrentValue().subtract(repaymentAmount));
				liabilityRepository.save(liability);
			}
		}
		
		refreshService.insertLog("부채 상환", repaymentAmount, name.replace("대출", "상환"), date);
	}

}
