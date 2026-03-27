package com.vegetable.service;

import com.vegetable.entity.Product;
import com.vegetable.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("商品不存在"));
    }

    public void updateStock(Long productId, Integer quantity) {
        Product product = getProductById(productId);
        if (product.getStock() < quantity) {
            throw new RuntimeException("商品库存不足");
        }
        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
    }
} 