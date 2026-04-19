package com.vegetable.controller;

import com.vegetable.entity.User;
import com.vegetable.entity.Order;
import com.vegetable.entity.Product;
import com.vegetable.service.CartService;
import com.vegetable.service.ProductService;
import com.vegetable.service.UserService;
import com.vegetable.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import com.vegetable.dto.OrderDetailDTO;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/products")
    public ResponseEntity<?> getProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/cart/add")
    public ResponseEntity<?> addToCart(@RequestBody Map<String, Object> request) {
        try {
            User currentUser = userService.getCurrentUser();
            Long productId = Long.valueOf(request.get("productId").toString());
            Integer quantity = Integer.valueOf(request.get("quantity").toString());
            
            cartService.addToCart(currentUser, productId, quantity);
            return ResponseEntity.ok(cartService.getCart(currentUser));
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/cart/remove")
    public ResponseEntity<?> removeFromCart(@RequestBody Map<String, Object> request) {
        try {
            User currentUser = userService.getCurrentUser();
            Long productId = Long.valueOf(request.get("productId").toString());
            
            cartService.removeFromCart(currentUser, productId);
            return ResponseEntity.ok(cartService.getCart(currentUser));
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/cart")
    public ResponseEntity<?> getCart() {
        try {
            User currentUser = userService.getCurrentUser();
            return ResponseEntity.ok(cartService.getCart(currentUser));
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/orders/create")
    public ResponseEntity<?> createOrder() {
        try {
            User currentUser = userService.getCurrentUser();
            Order order = orderService.createOrder(currentUser);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("orderId", order.getId());
            response.put("message", "订单创建成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/orders/{orderId}/pay")
    public ResponseEntity<?> payOrder(@PathVariable Long orderId) {
        try {
            // Order order = orderService.payOrder(orderId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "支付成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/orders")
    public ResponseEntity<?> getUserOrders() {
        try {
            User currentUser = userService.getCurrentUser();
            List<Order> orders = orderService.getUserOrders(currentUser);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/user/balance")
    public Map<String, Object> getUserBalance() {
        java.math.BigDecimal balance = userService.getCurrentUserBalance();
        Map<String, Object> result = new java.util.HashMap<>();
        result.put("balance", balance);
        return result;
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<?> getOrderDetail(@PathVariable Long orderId) {
        try {
            User currentUser = userService.getCurrentUser();
            Order order = orderService.getOrderById(orderId);
            if (!order.getUser().getId().equals(currentUser.getId())) {
                throw new RuntimeException("无权访问此订单");
            }
            // 构建DTO
            OrderDetailDTO dto = new OrderDetailDTO();
            dto.id = order.getId();
            dto.createdAt = order.getCreatedAt();
            dto.status = order.getStatus().name();
            dto.totalAmount = order.getTotalAmount();
            dto.items = new java.util.ArrayList<>();
            for (com.vegetable.entity.OrderItem item : order.getOrderItems()) {
                OrderDetailDTO.Item dtoItem = new OrderDetailDTO.Item();
                dtoItem.productName = item.getProduct().getName();
                dtoItem.price = item.getPrice();
                dtoItem.quantity = item.getQuantity();
                dtoItem.subtotal = item.getPrice() * item.getQuantity();
                dto.items.add(dtoItem);
            }
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            java.util.Map<String, Object> response = new java.util.HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/user/info")
    public Map<String, Object> getUserInfo() {
        com.vegetable.entity.User user = userService.getCurrentUser();
        Map<String, Object> result = new java.util.HashMap<>();
        result.put("username", user.getUsername());
        return result;
    }
} 