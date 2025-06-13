package com.scaler.productserviceapr21capstone.controllers;

import com.scaler.productserviceapr21capstone.dtos.ProductResponseDto;
import com.scaler.productserviceapr21capstone.dtos.SearchRequestDto;
import com.scaler.productserviceapr21capstone.models.Product;
import com.scaler.productserviceapr21capstone.services.SearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SearchController
{
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("/search")
    public Page<ProductResponseDto> search(@RequestBody SearchRequestDto searchRequestDto)
    {
        Page<Product> productPage = searchService.search(searchRequestDto.getQuery(),
                searchRequestDto.getPageNumber(),
                searchRequestDto.getPageSize(),
                searchRequestDto.getSortParam());

        return getProductResponseDtoPageFromProductPage(productPage);
    }

    @GetMapping("/search")
    public Page<ProductResponseDto> search(@RequestParam String query,
                                @RequestParam int pageNumber,
                                @RequestParam int pageSize,
                                @RequestParam String sortParam)
    {
        Page<Product> productPage = searchService.search(query, pageNumber, pageSize, sortParam);
        return getProductResponseDtoPageFromProductPage(productPage);

    }

    private Page<ProductResponseDto> getProductResponseDtoPageFromProductPage(Page<Product> productPage)
    {
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        List<Product> products = productPage.getContent();

        for(Product product : products)
        {
            ProductResponseDto productResponseDto = ProductResponseDto.from(product);
            productResponseDtos.add(productResponseDto);
        }

        return new PageImpl<>(productResponseDtos, productPage.getPageable(), productPage.getTotalElements());
    }
}
