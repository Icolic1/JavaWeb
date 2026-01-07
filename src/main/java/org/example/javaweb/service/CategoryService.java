package org.example.javaweb.service;

import org.example.javaweb.domain.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    Category findById(Long id);

    Category create(Category category);

    void delete(Long id);
}
