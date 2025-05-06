package com.scaler.productserviceapr21capstone.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.scaler.productserviceapr21capstone.dtos.CreateFakeStoreProductRequestDto;
import com.scaler.productserviceapr21capstone.exceptions.ProductNotFoundException;
import com.scaler.productserviceapr21capstone.models.Product;

import java.util.List;

public interface ProductService
{
    Product getProductById(long id) throws ProductNotFoundException;
    List<Product> getAllProducts();
    Product createProduct(String name, String description, double price, String imageUrl, String category);
    Product replaceProduct(long id, String name, String description, double price, String imageUrl, String category);
    Product applyPatchToProduct(long id, JsonPatch patch) throws ProductNotFoundException, JsonPatchException, JsonProcessingException;
}
