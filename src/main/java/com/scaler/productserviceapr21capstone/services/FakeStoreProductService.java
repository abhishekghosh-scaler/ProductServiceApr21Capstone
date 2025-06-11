package com.scaler.productserviceapr21capstone.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.scaler.productserviceapr21capstone.dtos.FakeStoreRequestDto;
import com.scaler.productserviceapr21capstone.dtos.FakeStoreResponseDto;
import com.scaler.productserviceapr21capstone.exceptions.ProductNotFoundException;
import com.scaler.productserviceapr21capstone.models.Product;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService
{
    RestTemplate restTemplate;
    RedisTemplate<String, Object> redisTemplate;

    public FakeStoreProductService(RestTemplate restTemplate,
                                   RedisTemplate<String, Object> redisTemplate)
    {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Product getProductById(long id) throws ProductNotFoundException
    {
        Product productFromRedis = (Product) redisTemplate.opsForValue().get(String.valueOf(id));
        if(productFromRedis != null)
        {
            return productFromRedis;
        }

       FakeStoreResponseDto fakeStoreResponseDto  = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + id,
                FakeStoreResponseDto.class);

       if(fakeStoreResponseDto == null)
       {
           throw new ProductNotFoundException("Product with id " + id + " not found.");
       }

       Product productFromFakeStore = fakeStoreResponseDto.toProduct();
       redisTemplate.opsForValue().set(String.valueOf(id), productFromFakeStore);
       return productFromFakeStore;
    }

    @Override
    public List<Product> getAllProducts()
    {
        FakeStoreResponseDto[] fakeStoreResponseDtos =
                restTemplate.getForObject(
                        "https://fakestoreapi.com/products",
                        FakeStoreResponseDto[].class);

        List<Product> products = new ArrayList<>();
        for(FakeStoreResponseDto fakeStoreResponseDto : fakeStoreResponseDtos)
        {
            Product product = fakeStoreResponseDto.toProduct();
            products.add(product);
        }

        return products;
    }

    @Override
    public Product createProduct(String name, String description, double price, String imageUrl, String category)
    {
        FakeStoreRequestDto fakeStoreRequestDto = createDtoFromParams(name, description, price, imageUrl, category);

        FakeStoreResponseDto fakeStoreResponseDto = restTemplate.postForObject(
                "https://fakestoreapi.com/products",
                fakeStoreRequestDto,
                FakeStoreResponseDto.class);

        return fakeStoreResponseDto.toProduct();
    }

    private FakeStoreRequestDto createDtoFromParams(String name, String description, double price, String imageUrl, String category)
    {
        FakeStoreRequestDto fakeStoreRequestDto = new FakeStoreRequestDto();
        fakeStoreRequestDto.setTitle(name);
        fakeStoreRequestDto.setDescription(description);
        fakeStoreRequestDto.setPrice(price);
        fakeStoreRequestDto.setImage(imageUrl);
        fakeStoreRequestDto.setCategory(category);
        return fakeStoreRequestDto;
    }

    @Override
    public Product replaceProduct(long id, String name, String description, double price, String imageUrl, String category)
    {
        FakeStoreRequestDto updatedFakeStoreRequestDto = createDtoFromParams(name, description, price, imageUrl, category);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<FakeStoreRequestDto> requestEntity =
                new HttpEntity<>(updatedFakeStoreRequestDto, headers);

        ResponseEntity<FakeStoreResponseDto> responseEntity = restTemplate.exchange(
                "https://fakestoreapi.com/products/" + id,
                HttpMethod.PUT,
                requestEntity,
                FakeStoreResponseDto.class
        );

        return responseEntity.getBody().toProduct();

    }

    @Override
    public Product applyPatchToProduct(long id, JsonPatch patch)
            throws ProductNotFoundException,
            JsonPatchException,
            JsonProcessingException {

        // Get existing product
        Product existingProduct = getProductById(id);

        // Convert Product to JSON Format
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode productNode = objectMapper.valueToTree(existingProduct);

        // Apply Patch
        JsonNode patchedNode = patch.apply(productNode);

        //Convert back to Product
        Product patchedProduct = objectMapper.treeToValue(patchedNode, Product.class);

        return replaceProduct(id,
                patchedProduct.getName(),
                patchedProduct.getDescription(),
                patchedProduct.getPrice(),
                patchedProduct.getCategory().getName(),
                patchedProduct.getImageUrl()
        );
    }
}
