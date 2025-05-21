package com.scaler.productserviceapr21capstone.controllers;

import com.scaler.productserviceapr21capstone.dtos.ProductResponseDto;
import com.scaler.productserviceapr21capstone.exceptions.ProductNotFoundException;
import com.scaler.productserviceapr21capstone.models.Category;
import com.scaler.productserviceapr21capstone.models.Product;
import com.scaler.productserviceapr21capstone.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest
{
    @MockitoBean
    @Qualifier("productDbService")
    ProductService productService;

    @Autowired
    ProductController productController;

    @Test
    public void testGetProductByIdReturnsDto() throws ProductNotFoundException
    {
        Product dummyProduct = new Product();
        dummyProduct.setId(1L);
        dummyProduct.setName("Product 1");
        dummyProduct.setDescription("Product 1 description");
        dummyProduct.setPrice(12.5);
        dummyProduct.setImageUrl("img.url.dummy");

        Category dummyCategory = new Category();
        dummyCategory.setId(1L);
        dummyCategory.setName("Category 1");

        dummyProduct.setCategory(dummyCategory);

        when(productService.getProductById(1L)).thenReturn(dummyProduct);

        ProductResponseDto productResponseDto = productController.getProductById(1L);

        assertEquals(1L, productResponseDto.getId());
        assertEquals("Product 1", productResponseDto.getName());
        assertEquals("Product 1 description", productResponseDto.getDescription());
        assertEquals("img.url.dummy", productResponseDto.getImageUrl());
        assertEquals(12.5, productResponseDto.getPrice());
        assertEquals("Category 1", productResponseDto.getCategory());
    }

    @Test
    public void testGetProductByIdReturnsNull() throws ProductNotFoundException
    {
        when(productService.getProductById(1L)).thenReturn(null);
        ProductResponseDto productResponseDto = productController.getProductById(1L);
        assertNull(productResponseDto);
    }
}