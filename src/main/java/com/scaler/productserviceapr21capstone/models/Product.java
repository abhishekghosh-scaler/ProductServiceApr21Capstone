package com.scaler.productserviceapr21capstone.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;

@Getter
@Setter
@Entity
public class Product extends Base
{
    private double price;
    private String description;
    private double weight;
    private String imageUrl;
    @ManyToOne
    private Category category;
}

