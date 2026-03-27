package com.vegetable.controller;

import com.vegetable.entity.Order;
import com.vegetable.entity.User;
import com.vegetable.service.OrderService;
import com.vegetable.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String orderHistory(Model model) {
        User currentUser = userService.getCurrentUser();
        List<Order> orders = orderService.getUserOrders(currentUser);
        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping("/{orderId}/details")
    @ResponseBody
    public ResponseEntity<?> getOrderDetails(@PathVariable Long orderId) {
        try {
            User currentUser = userService.getCurrentUser();
            Order order = orderService.getOrderById(orderId);
            
            // 验证订单是否属于当前用户
            if (!order.getUser().getId().equals(currentUser.getId())) {
                throw new RuntimeException("无权访问此订单");
            }
            
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
} 