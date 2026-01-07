package org.example.javaweb.service.impl;

import org.example.javaweb.domain.Product;
import org.example.javaweb.repository.ProductRepository;
import org.example.javaweb.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public List<Product> findAllWithCategory() {
        return productRepository.findAllWithCategory();
    }

    @Override
    public List<Product> findByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }


    @Override
    public Product findByIdWithCategory(Long id) {
        return productRepository.findByIdWithCategory(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }



    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product update(Long id, Product product) {
        Product existing = findById(id);

        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());
        existing.setCategory(product.getCategory());
        existing.setStock(product.getStock());

        return productRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
