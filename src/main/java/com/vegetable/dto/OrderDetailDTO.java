package com.vegetable.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDetailDTO {
    public static class Item {
        public String productName;
        public Double price;
        public Integer quantity;
        public Double subtotal;
    }
    public Long id;
    public LocalDateTime createdAt;
    public String status;
    public Double totalAmount;
    public List<Item> items;
} 