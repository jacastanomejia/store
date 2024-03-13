package com.onlinestore.productservice.service;

import com.onlinestore.productservice.entity.Category;
import com.onlinestore.productservice.entity.Product;
import com.onlinestore.productservice.repository.CategoryRepository;
import com.onlinestore.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<Product> listAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product create(Product product) {
        if(product.getCategory() == null){
            throw new RuntimeException("No category defined");
        }

        Optional<Category> oc = categoryRepository.findById(product.getCategory().getId());
        if(oc.isEmpty()){
            categoryRepository.save(product.getCategory());
        }

        product.setStatus("created");
        product.setCreatedAt(Calendar.getInstance().getTime());
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        Optional<Product> pp = productRepository.findById(product.getId());

        if(pp.isEmpty()){
            return null;
        }

        Product original = pp.get();
        original.setName(product.getName());
        original.setDescription(product.getDescription());
        original.setStatus("updated");
        original.setPrice(product.getPrice());

        return productRepository.save(original);
    }

    @Override
    public boolean delete(Long id) {
        Optional<Product> pp = productRepository.findById(id);

        if(pp.isEmpty()) return false;
        productRepository.delete(pp.get());

        return true;
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public Product updateStock(Long id, Double quantity) {
        Optional<Product> pp = productRepository.findById(id);

        if(pp.isEmpty()) return null;
        Product product = pp.get();
        product.setStock(product.getStock() + quantity);
        return productRepository.save(product);
    }
}
