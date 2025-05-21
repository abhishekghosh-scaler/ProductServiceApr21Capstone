package com.scaler.productserviceapr21capstone.services;

import com.scaler.productserviceapr21capstone.dtos.FakeStoreRequestDto;
import com.scaler.productserviceapr21capstone.dtos.FakeStoreResponseDto;
import com.scaler.productserviceapr21capstone.exceptions.ProductNotFoundException;
import com.scaler.productserviceapr21capstone.models.Product;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class FakeStoreProductServiceTest
{
    RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
    FakeStoreProductService fakeStoreProductService = new FakeStoreProductService(restTemplate);

    private FakeStoreResponseDto getDummyFakestoreResponseDto()
    {
        FakeStoreResponseDto dummyResponse = new FakeStoreResponseDto();
        dummyResponse.setId(1L);
        dummyResponse.setTitle("title");
        dummyResponse.setDescription("description");
        dummyResponse.setPrice(1.0);
        dummyResponse.setImage("img.dummy");
        dummyResponse.setCategory("category");

        return dummyResponse;
    }

    @Test
    public void testGetProductByIdReturnsProduct() throws ProductNotFoundException
    {
        FakeStoreResponseDto dummyResponse = getDummyFakestoreResponseDto();

        when(restTemplate.getForObject(
                "https://fakestoreapi.com/products/1",
                FakeStoreResponseDto.class)).thenReturn(dummyResponse);

        Product product = fakeStoreProductService.getProductById(1L);

        assertEquals(1L, product.getId());
        assertEquals("title", product.getName());
    }

    @Test
    public void testGetProductByIdThrowsExceptionOnValueNull() throws ProductNotFoundException
    {
        when(restTemplate.getForObject(
                "https://fakestoreapi.com/products/1",
                FakeStoreResponseDto.class)).thenReturn(null);

        //Act & Assert
        assertThrows(ProductNotFoundException.class,
                () -> fakeStoreProductService.getProductById(1L));

    }

    @Test
    public void testCreateProductReturnsProduct()
    {
        FakeStoreResponseDto dummyResponse = getDummyFakestoreResponseDto();

        when(restTemplate.postForObject(
                eq("https://fakestoreapi.com/products"),
                any(),
                eq(FakeStoreResponseDto.class))).thenReturn(dummyResponse);

        Product product = fakeStoreProductService.createProduct("title", "description",
                1.0, "img.url", "category");

        assertEquals(1L, product.getId());
        assertEquals("title", product.getName());
    }

    @Test
    public void testCreateProductReturnsProductVerifyAPICalls()
    {
        FakeStoreResponseDto dummyResponse = getDummyFakestoreResponseDto();

        when(restTemplate.postForObject(
                eq("https://fakestoreapi.com/products"),
                any(),
                eq(FakeStoreResponseDto.class))).thenReturn(dummyResponse);

        Product product = fakeStoreProductService.createProduct("title", "description",
                1.0, "img.url", "category");

        verify(restTemplate, times(1)).postForObject(
                eq("https://fakestoreapi.com/products"),
                any(),
                eq(FakeStoreResponseDto.class));
    }

}