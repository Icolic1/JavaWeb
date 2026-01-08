package org.example.javaweb.web.dto;

import java.math.BigDecimal;

public record ProductListDto(
        Long id,
        String name,
        String description,
        BigDecimal price,
        String categoryName,
        Integer stock
) {}
