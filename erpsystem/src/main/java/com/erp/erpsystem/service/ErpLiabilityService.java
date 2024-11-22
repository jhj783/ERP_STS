package com.erp.erpsystem.service;

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
	
	public List<Liability> getLiability() {
        return liabilityRepository.findLiabilityData();
	}
}
