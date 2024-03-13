package com.onlinestore.productservice.service;

import com.onlinestore.productservice.entity.Category;
import com.onlinestore.productservice.entity.Product;

import java.util.List;

public interface IProductService {
    public List<Product> listAllProducts();
    public Product getProduct(Long id);

    public Product create(Product product);

    public Product update(Product product);

    public boolean delete(Long id);

    public List<Product> findByCategory(Category category);

    public Product updateStock(Long id, Double quantity);
}
