package com.scaler.productserviceapr21capstone.controllers;

import com.scaler.productserviceapr21capstone.dtos.ProductResponseDto;
import com.scaler.productserviceapr21capstone.models.Product;
import com.scaler.productserviceapr21capstone.services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController
{
    ProductService productService;

    public ProductController(ProductService productService)
    {
        this.productService = productService;
    }

    @GetMapping("/products/{id}")
    public ProductResponseDto getProductById(@PathVariable("id") long id)
    {
        Product product = productService.getProductById(id);

        return ProductResponseDto.from(product);
    }
}
