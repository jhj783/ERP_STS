package com.erp.erpsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EnemyController {

    @GetMapping("/charts")
    public String showAsdfPage() {
        return "Charts";  // asdf.html을 반환
    }
}