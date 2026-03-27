package com.vegetable.controller;

import com.vegetable.entity.User;
import com.vegetable.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        try {
            userService.registerUser(user);
            return "redirect:/user/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        userService.logout();
        return "redirect:/user/login";
    }

    @PostMapping("/api/register")
    @ResponseBody
    public Map<String, Object> apiRegister(@RequestBody Map<String, String> params) {
        Map<String, Object> result = new HashMap<>();
        try {
            User user = new User();
            user.setUsername(params.get("username"));
            user.setPassword(params.get("password"));
            user.setEmail(params.get("email"));
            userService.registerUser(user);
            result.put("success", true);
            result.put("message", "注册成功");
        } catch (RuntimeException e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
} 