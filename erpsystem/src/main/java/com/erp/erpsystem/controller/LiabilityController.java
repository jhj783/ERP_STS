package com.erp.erpsystem.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.erp.erpsystem.service.ErpLiabilityService;

@RestController
public class LiabilityController {
    @Autowired
    private ErpLiabilityService erpLiabilityService;

    @PostMapping("/repayDebt")
    public ResponseEntity<String> repayDebt(@RequestBody Map<String, Object> request) {
        String name = (String) request.get("name");
        String amount = (String) request.get("amount");
        Boolean isFullRepayment = (Boolean) request.get("isFullRepayment");

        if (name == null || (amount == null && !Boolean.TRUE.equals(isFullRepayment))) {
            return ResponseEntity.badRequest().body("부채 이름과 금액 또는 완전 상환 여부가 필요합니다.");
        }

        try {
            erpLiabilityService.debtRepayment(name, amount, Boolean.TRUE.equals(isFullRepayment));
            return ResponseEntity.ok("부채 상환 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("부채 상환 실패: " + e.getMessage());
        }
    }
}
