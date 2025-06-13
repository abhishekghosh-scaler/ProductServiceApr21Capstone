package com.scaler.productserviceapr21capstone.repositories;

import com.scaler.productserviceapr21capstone.dtos.ProductProjection;
import com.scaler.productserviceapr21capstone.dtos.ProductProjectionDto;
import com.scaler.productserviceapr21capstone.models.Category;
import com.scaler.productserviceapr21capstone.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    List<Product> findByCategory(Category category);

    List<Product> findByCategory_Name(String name);

    @Query("select p from Product p where p.category.name = :categoryName")
    List<Product> getProductsByCategoryName(@Param("categoryName") String categoryName);

    @Query(value = CustomNativeQuery.GET_PRODUCT_FROM_CATEGORY_NAME, nativeQuery = true)
    List<Product> getProductsByCategoryNameNative(@Param("categoryName") String categoryName);

    @Query("select p.name, p. price from Product p where p.category.name = :categoryName")
    List<ProductProjection> getProjectedProduct(@Param("categoryName") String categoryName);

    @Query("select new com.scaler.productserviceapr21capstone.dtos.ProductProjectionDto(p.name, p. price) from Product p where p.category.name = :categoryName")
    List<ProductProjectionDto> getProjectedProductDto(@Param("categoryName") String categoryName);

    Page<Product> findByNameContaining(String query, Pageable pageable);
}


