package org.example.javaweb.repository;

import org.example.javaweb.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryId(Long categoryId);

    boolean existsByNameIgnoreCase(String name);

    @Query("select p from Product p join fetch p.category")
    List<Product> findAllWithCategory();

    @Query("select p from Product p join fetch p.category where p.id = :id")
    Optional<Product> findByIdWithCategory(@Param("id") Long id);
}
