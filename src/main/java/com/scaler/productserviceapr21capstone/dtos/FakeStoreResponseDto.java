package com.scaler.productserviceapr21capstone.dtos;

import com.scaler.productserviceapr21capstone.models.Category;
import com.scaler.productserviceapr21capstone.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreResponseDto
{
    private long id;
    private String title;
    private double price;
    private String description;
    private String image;
    private String category;

    public Product toProduct()
    {
        Product product = new Product();
        product.setId(id);
        product.setName(title);
        product.setDescription(description);
        product.setImageUrl(image);
        product.setPrice(price);

        Category category1 = new Category();
        category1.setName(category);

        product.setCategory(category1);

        return product;
    }
}
