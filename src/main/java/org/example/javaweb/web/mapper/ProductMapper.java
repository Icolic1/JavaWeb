package org.example.javaweb.web.mapper;

import org.example.javaweb.domain.Product;
import org.example.javaweb.web.dto.ProductDetailsDto;
import org.example.javaweb.web.dto.ProductFormDto;
import org.example.javaweb.web.dto.ProductListDto;

public class ProductMapper {

    private ProductMapper() {}

    public static ProductListDto toListDto(Product p) {
        return new ProductListDto(
                p.getId(),
                p.getName(),
                p.getPrice(),
                p.getCategory() != null ? p.getCategory().getName() : null,
                p.getStock()
        );
    }

    public static ProductDetailsDto toDetailsDto(Product p) {
        return new ProductDetailsDto(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getPrice(),
                p.getStock(),
                p.getCategory() != null ? p.getCategory().getId() : null,
                p.getCategory() != null ? p.getCategory().getName() : null
        );
    }

    public static ProductFormDto toFormDto(Product p) {
        ProductFormDto dto = new ProductFormDto();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setDescription(p.getDescription());
        dto.setPrice(p.getPrice());
        dto.setStock(p.getStock());
        dto.setCategoryId(p.getCategory() != null ? p.getCategory().getId() : null);
        return dto;
    }
}
