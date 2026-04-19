package com.vegetable.controller;

import com.vegetable.entity.User;
import com.vegetable.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                       @RequestParam String password,
                       RedirectAttributes redirectAttributes) {
        try {
            // User user = userService.login(username, password);
            // 登录成功，重定向到主页面
            return "redirect:/main";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/user/login";
        }
    }

    // 新增：API风格的登录接口，返回JSON
    @PostMapping("/api/login")
    @ResponseBody
    public Map<String, Object> apiLogin(@RequestBody Map<String, String> params) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        try {
            String username = params.get("username");
            String password = params.get("password");
            User user = userService.login(username, password);
            // 登录成功，返回详细用户信息
            data.put("id", user.getId());
            data.put("name", user.getUsername());
            data.put("email", user.getEmail());
            data.put("balance", user.getBalance());
            data.put("createdAt", user.getCreatedAt());
            data.put("updatedAt", user.getUpdatedAt());
            result.put("action", "add_customer");
            result.put("data", data);
        } catch (Exception e) {
            result.put("action", "login_failed");
            data.put("error", e.getMessage());
            result.put("data", data);
        }
        return result;
    }
} 