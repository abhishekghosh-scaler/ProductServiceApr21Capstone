package com.scaler.productserviceapr21capstone.repositories;

import com.scaler.productserviceapr21capstone.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/*
* 1st Argument: Entity name for the repository
* 2nd Argument: Type of primary key
* */
public interface ProductRepository extends JpaRepository<Product, Long>
{
    Product save(Product product);

    Optional<Product> findById(long id);

    List<Product> findAll();
}
