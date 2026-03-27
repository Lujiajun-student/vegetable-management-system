package com.vegetable.controller;

import com.vegetable.entity.User;
import com.vegetable.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/recharge")
public class RechargeController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String rechargePage(Model model) {
        User currentUser = userService.getCurrentUser();
        model.addAttribute("userBalance", currentUser.getBalance());
        return "recharge";
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<?> addBalance(@RequestBody Map<String, Object> request) {
        try {
            User currentUser = userService.getCurrentUser();
            BigDecimal amount = new BigDecimal(request.get("amount").toString());
            
            userService.addBalance(currentUser, amount);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "充值成功");
            response.put("newBalance", currentUser.getBalance());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
} 