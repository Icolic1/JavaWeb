package org.example.javaweb.web.dto;

import java.math.BigDecimal;

public record ProductDetailsDto(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        Long categoryId,
        String categoryName
) {}
