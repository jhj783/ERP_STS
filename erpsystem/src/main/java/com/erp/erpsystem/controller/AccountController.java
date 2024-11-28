package com.erp.erpsystem.controller;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.erp.erpsystem.db.Account;
import com.erp.erpsystem.db.AccountRepository;

@Controller
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/account")
    public String getAccounts(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "15") int size,
            Model model) {

        Page<Account> accountPage = accountRepository.findAll(
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "date")));

        model.addAttribute("accounts", accountPage.getContent());
        model.addAttribute("totalPages", accountPage.getTotalPages());
        model.addAttribute("currentPage", page);
        return "Account";
    }
    
    @GetMapping("/account/send")
    public String showSendPage() {
        return "AccountSendMoney";
    }


}
