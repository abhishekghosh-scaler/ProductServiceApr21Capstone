package com.scaler.productserviceapr21capstone.services;

import com.scaler.productserviceapr21capstone.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductCacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String PRODUCT_KEY_PREFIX = "product:";

    public void saveProduct(Product product) {
        redisTemplate.opsForValue().set(PRODUCT_KEY_PREFIX + product.getId(), product);
    }

    public Product getProduct(Long id) {
        return (Product) redisTemplate.opsForValue().get(PRODUCT_KEY_PREFIX + id);
    }
}
