package com.scaler.productserviceapr21capstone.models;

import lombok.*;

@Getter
@Setter
public class Product
{
    private long id;
    private String name;
    private double price;
    private String description;
    private String imageUrl;
    private Category category;
}

