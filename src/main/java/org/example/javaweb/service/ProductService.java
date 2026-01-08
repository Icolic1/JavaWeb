package org.example.javaweb.service;

import org.example.javaweb.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAllWithCategory();
    Product findByIdWithCategory(Long id);
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByCategoryIdWithCategory(Long categoryId);


    List<Product> findAll();
    Product findById(Long id);
    Product create(Product product);
    Product update(Long id, Product product);
    void delete(Long id);
}
