package com.erp.erpsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EnemyController {

    @GetMapping("/charts")
    public String showChartsPage() {
        return "Charts";  // asdf.html을 반환
    }
    
    @GetMapping("/testc")
    public String showTestPage() {
        return "testCharts";  // asdf.html을 반환
    }
    
}