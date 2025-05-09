package com.scaler.productserviceapr21capstone.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.scaler.productserviceapr21capstone.dtos.CreateFakeStoreProductRequestDto;
import com.scaler.productserviceapr21capstone.dtos.ErrorDto;
import com.scaler.productserviceapr21capstone.dtos.ProductResponseDto;
import com.scaler.productserviceapr21capstone.exceptions.ProductNotFoundException;
import com.scaler.productserviceapr21capstone.models.Product;
import com.scaler.productserviceapr21capstone.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController
{
    ProductService productService;

    public ProductController(
            @Qualifier("productDbService") ProductService productService
    )
    {
        this.productService = productService;
    }

    @GetMapping("/products/{id}")
    public ProductResponseDto getProductById(@PathVariable("id") long id)
            throws ProductNotFoundException {
        Product product = productService.getProductById(id);

        return ProductResponseDto.from(product);
    }

    @GetMapping("/products")
    public List<ProductResponseDto> getAllProducts()
    {
        List<Product> products = productService.getAllProducts();

//        List<ProductResponseDto> productResponseDtos =
//                products.stream().map(ProductResponseDto::from)
//                        .collect(Collectors.toList());

        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for(Product product : products)
        {
            ProductResponseDto productResponseDto = ProductResponseDto.from(product);
            productResponseDtos.add(productResponseDto);
        }

        return productResponseDtos;
    }

    @PostMapping("/products")
    public ResponseEntity<ProductResponseDto> createProduct(
            @RequestBody CreateFakeStoreProductRequestDto
                    createFakeStoreProductRequestDto)
    {
        Product product = productService.createProduct(
                createFakeStoreProductRequestDto.getName(),
                createFakeStoreProductRequestDto.getDescription(),
                createFakeStoreProductRequestDto.getPrice(),
                createFakeStoreProductRequestDto.getImageUrl(),
                createFakeStoreProductRequestDto.getCategory()
        );
        return new ResponseEntity<>(ProductResponseDto.from(product), HttpStatus.CREATED);
    }

    @PutMapping("/products/{id}")
    public ProductResponseDto replaceProduct(@PathVariable("id") long id,
            @RequestBody CreateFakeStoreProductRequestDto
                    createFakeStoreProductRequestDto)
    {
        Product product = productService.replaceProduct(
                id,
                createFakeStoreProductRequestDto.getName(),
                createFakeStoreProductRequestDto.getDescription(),
                createFakeStoreProductRequestDto.getPrice(),
                createFakeStoreProductRequestDto.getImageUrl(),
                createFakeStoreProductRequestDto.getCategory()
        );

        return ProductResponseDto.from(product);
    }

    /*
    * NOTES for configuring POSTMAN correctly
    * URL: http://localhost:8080/products/4
    * METHOD: PATCH
    * Headers: "Content-Type: application/json-patch+json"
    * BODY:
    * [
            {
                "op": "replace",
                "path": "/price",
                "value": 0.99
            }
       ]
    *
    *
    * */
    @PatchMapping(
            path = "/products/{id}",
            consumes = "application/json-patch+json" // REQUIRED
    )
    public ProductResponseDto updateProduct(
            @PathVariable("id") long id,
            @RequestBody JsonPatch jsonPatch
    ) throws ProductNotFoundException, JsonPatchException, JsonProcessingException
    {
        Product product = productService.applyPatchToProduct(id, jsonPatch);
        return ProductResponseDto.from(product);
    }

//    @ExceptionHandler(NullPointerException.class)
//    public ErrorDto handleNullPointerException()
//    {
//        ErrorDto errorDto = new ErrorDto();
//        errorDto.setStatus("Failure");
//        errorDto.setMessage("Product cannot be null");
//
//        return errorDto;
//    }
}
