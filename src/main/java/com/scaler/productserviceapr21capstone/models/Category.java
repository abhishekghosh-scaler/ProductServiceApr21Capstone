package com.scaler.productserviceapr21capstone.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Category extends Base
{
    @OneToMany(mappedBy = "category")
    List<Product> products;
}
