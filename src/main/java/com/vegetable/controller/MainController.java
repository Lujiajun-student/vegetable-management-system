package com.vegetable.controller;

import com.vegetable.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.math.BigDecimal;

@Controller
public class MainController {
    @Autowired
    private UserService userService;

    @GetMapping("/main")
    public String mainPage(Model model) {
        // 获取当前登录用户的余额
        BigDecimal balance = userService.getCurrentUserBalance();
        model.addAttribute("userBalance", balance);
        return "main";
    }
} 