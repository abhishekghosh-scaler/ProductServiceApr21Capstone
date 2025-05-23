package com.scaler.productserviceapr21capstone.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaler.productserviceapr21capstone.dtos.CreateFakeStoreProductRequestDto;
import com.scaler.productserviceapr21capstone.dtos.ProductResponseDto;
import com.scaler.productserviceapr21capstone.models.Category;
import com.scaler.productserviceapr21capstone.models.Product;
import com.scaler.productserviceapr21capstone.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerWebMvcTest
{
    @MockitoBean
    @Qualifier("productDbService")
    ProductService productService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Product getProductForTest()
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

        return dummyProduct;
    }

    @Test
    public void testGetAllProductsRunsSuccessfully() throws Exception {
        //Arrange
        Product dummyProduct1 = getProductForTest();
        Product dummyProduct2 = getProductForTest();
        dummyProduct2.setId(2L);
        List<Product> dummyProducts = List.of(dummyProduct1, dummyProduct2);

        List<ProductResponseDto> dummyProductResponseDtos
                = List.of(ProductResponseDto.from(dummyProduct1), ProductResponseDto.from(dummyProduct2));

        when(productService.getAllProducts()).thenReturn(dummyProducts);

        //Act & Assert
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(dummyProductResponseDtos)));

    }

    @Test
    public void testCreateProductsRunsSuccessfully() throws Exception
    {
        CreateFakeStoreProductRequestDto dummyRequestDto = new CreateFakeStoreProductRequestDto();
        dummyRequestDto.setName("Product 1");
        dummyRequestDto.setDescription("Product 1 description");
        dummyRequestDto.setPrice(12.5);
        dummyRequestDto.setImageUrl("img.url.dummy");
        dummyRequestDto.setCategory("Category 1");

        Product productAfterSave = getProductForTest();
        ProductResponseDto dummyResponseDto = ProductResponseDto.from(productAfterSave);

        when(productService.createProduct(dummyRequestDto.getName(), dummyRequestDto.getDescription(),
                dummyRequestDto.getPrice(), dummyRequestDto.getImageUrl(),
                dummyRequestDto.getCategory()))
                .thenReturn(productAfterSave);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dummyRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(dummyResponseDto)));
    }
}
