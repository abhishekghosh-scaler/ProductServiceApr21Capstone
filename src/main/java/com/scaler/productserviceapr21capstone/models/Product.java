package com.scaler.productserviceapr21capstone.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@Entity
public class Product extends Base
{
    private double price;
    private String description;
    private String imageUrl;
    @ManyToOne
    private Category category;
}

