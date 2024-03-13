package com.onlinestore.productservice.repository;

import com.onlinestore.productservice.entity.Category;
import com.onlinestore.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    public List<Product> findByCategory(Category category);
}
