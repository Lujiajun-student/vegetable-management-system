package com.vegetable.service;

import com.vegetable.entity.*;
import com.vegetable.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Transactional
    public Order createOrder(User user) {
        // 获取购物车内容
        Map<String, Object> cart = cartService.getCart(user);
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> items = (List<Map<String, Object>>) cart.get("items");
        double total = ((Number) cart.get("total")).doubleValue();

        // 检查库存
        for (Map<String, Object> item : items) {
            Long productId = ((Number) item.get("productId")).longValue();
            Integer quantity = ((Number) item.get("quantity")).intValue();
            Product product = productService.getProductById(productId);
            if (product.getStock() < quantity) {
                throw new RuntimeException("商品 " + product.getName() + " 库存不足");
            }
        }

        // 检查余额
        BigDecimal userBalance = userService.getCurrentUserBalance();
        if (userBalance.compareTo(BigDecimal.valueOf(total)) < 0) {
            throw new RuntimeException("余额不足");
        }

        // 创建订单
        final Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(total);
        order.setStatus(OrderStatus.PAID);

        // 创建订单项
        List<OrderItem> orderItems = items.stream()
                .map(item -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setProduct(productService.getProductById(((Number) item.get("productId")).longValue()));
                    orderItem.setQuantity(((Number) item.get("quantity")).intValue());
                    orderItem.setPrice(((Number) item.get("price")).doubleValue());
                    return orderItem;
                })
                .collect(Collectors.toList());
        order.setOrderItems(orderItems);

        // 保存订单
        Order savedOrder = orderRepository.save(order);

        // 扣除库存
        for (Map<String, Object> item : items) {
            Long productId = ((Number) item.get("productId")).longValue();
            Integer quantity = ((Number) item.get("quantity")).intValue();
            productService.updateStock(productId, quantity);
        }

        // 扣除余额
        userService.deductBalance(user, BigDecimal.valueOf(total));

        // 清空购物车
        cartService.clearCart(user);

        return savedOrder;
    }

    @Transactional
    public Order payOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("订单不存在"));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("订单状态不正确");
        }

        order.setStatus(OrderStatus.PAID);
        return orderRepository.save(order);
    }

    public List<Order> getUserOrders(User user) {
        return orderRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("订单不存在"));
    }
} 