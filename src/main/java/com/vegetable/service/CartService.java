package com.vegetable.service;

import com.vegetable.entity.CartItem;
import com.vegetable.entity.Product;
import com.vegetable.entity.User;
import com.vegetable.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    public Map<String, Object> getCart(User user) {
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        double total = cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        List<Map<String, Object>> items = cartItems.stream()
                .map(item -> {
                    Map<String, Object> itemMap = new HashMap<>();
                    itemMap.put("productId", item.getProduct().getId());
                    itemMap.put("productName", item.getProduct().getName());
                    itemMap.put("price", item.getProduct().getPrice());
                    itemMap.put("quantity", item.getQuantity());
                    return itemMap;
                })
                .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("items", items);
        result.put("total", total);
        return result;
    }

    @Transactional
    public void addToCart(User user, Long productId, Integer quantity) {
        Product product = productService.getProductById(productId);
        
        CartItem existingItem = cartItemRepository.findByUser(user).stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            cartItemRepository.save(existingItem);
        } else {
            CartItem newItem = new CartItem();
            newItem.setUser(user);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cartItemRepository.save(newItem);
        }
    }

    @Transactional
    public void removeFromCart(User user, Long productId) {
        cartItemRepository.deleteByUserAndProductId(user, productId);
    }

    @Transactional
    public void clearCart(User user) {
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        cartItemRepository.deleteAll(cartItems);
    }
} 