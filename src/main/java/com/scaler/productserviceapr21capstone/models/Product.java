package com.scaler.productserviceapr21capstone.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;

@Getter
@Setter
@Entity
public class Product extends Base implements Serializable
{
    private double price;
    private String description;
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private Category category;
}

