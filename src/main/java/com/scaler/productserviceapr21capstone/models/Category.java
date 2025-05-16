package com.scaler.productserviceapr21capstone.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Getter
@Setter
@Entity
public class Category extends Base
{
    @OneToMany(mappedBy = "category")
    @Fetch(FetchMode.SUBSELECT)
    List<Product> products;
}
