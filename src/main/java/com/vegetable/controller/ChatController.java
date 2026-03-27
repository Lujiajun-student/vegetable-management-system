package com.vegetable.controller;

import com.vegetable.AI.Chat;
import com.vegetable.service.UserService;
import com.vegetable.service.OrderService;
import com.vegetable.entity.User;
import com.vegetable.entity.Order;
import com.vegetable.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class ChatController {
    
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/chat")
    public String chatPage() {
        return "chat";
    }

    @PostMapping("/chat")
    @ResponseBody
    public Map<String, String> chat(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        User user = userService.getCurrentUser();
        java.util.List<Order> orders = orderService.getUserOrders(user);
        StringBuilder orderInfo = new StringBuilder();
        if (!orders.isEmpty()) {
            orderInfo.append("用户历史订单如下：\n");
            for (Order order : orders) {
                orderInfo.append("订单号:").append(order.getId())
                        .append(", 时间:").append(order.getCreatedAt())
                        .append(", 总金额:¥").append(order.getTotalAmount())
                        .append(", 商品:");
                for (OrderItem item : order.getOrderItems()) {
                    orderInfo.append(item.getProduct().getName())
                            .append("(").append(item.getQuantity()).append("件, 单价¥")
                            .append(item.getPrice()).append(") ");
                }
                orderInfo.append("\n");
            }
        } else {
            orderInfo.append("用户暂无历史订单。\n");
        }
        String systemPrompt = "你是一个蔬菜销售助手，名字叫派蒙。" + orderInfo.toString() + "请根据用户历史订单和问题，给出最合适的蔬菜推荐。可用的蔬菜分类有：" +
                "叶菜类、根茎类、瓜类、豆类、茄果类、肉类。注意，回答时尽量不要使用*。";
        String response = com.vegetable.AI.Chat.chatWithSystemPrompt(systemPrompt, message);
        return Map.of("response", response);
    }
} 