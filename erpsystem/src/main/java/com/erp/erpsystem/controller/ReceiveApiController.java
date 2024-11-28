package com.erp.erpsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.erp.erpsystem.service.SendMoneyApiService;

@Controller
public class ReceiveApiController {

    @Autowired
    private SendMoneyApiService sendMoneyApiService;

    @PostMapping("/processDeposit")
    public String processDeposit(
        @RequestParam("recipient") String recipient, 
        @RequestParam("amount") String amount
    ) {
        String description = recipient;
        sendMoneyApiService.sendMoneyApi(description, amount);

        // 처리 완료 후 리다이렉트
        return "redirect:/account";
    }
}
