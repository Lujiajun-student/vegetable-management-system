package com.vegetable.repository;

import com.vegetable.entity.CartItem;
import com.vegetable.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
    void deleteByUserAndProductId(User user, Long productId);
} 