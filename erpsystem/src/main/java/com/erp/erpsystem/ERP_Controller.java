package com.erp.erpsystem;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ERP_Controller {
    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello World";
    }
    
    @GetMapping("/test")
    public String showTestPage() {
        return "test"; // "test"는 templates 폴더의 test.html 파일을 의미합니다.
    }
    
}