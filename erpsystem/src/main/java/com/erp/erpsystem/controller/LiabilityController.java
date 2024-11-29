package com.erp.erpsystem.controller;

import com.erp.erpsystem.service.ErpLiabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class LiabilityController {

    @Autowired
    private ErpLiabilityService erpLiabilityService;

    // 부채 상환 처리
    @PostMapping("/repayDebt")
    public String repayDebt(
            @RequestParam("name") String name,
            @RequestParam("amount") String amountStr,
            Model model) {

        if (name == null || amountStr == null) {
            model.addAttribute("errorMessage", "부채 이름과 상환 금액을 모두 입력해야 합니다.");
            return "LiabilityList";
        }

        try {
            erpLiabilityService.debtRepayment(name, amountStr, false);
            return "redirect:/liability"; // 상환 후 부채 목록 페이지로 리다이렉트
        } catch (Exception e) {
            model.addAttribute("errorMessage", "부채 상환 실패: " + e.getMessage());
            return "Liability";
        }
    }
}
