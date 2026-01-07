package org.example.javaweb.web.dto;

import java.math.BigDecimal;

public record ProductListDto(
        Long id,
        String name,
        BigDecimal price,
        String categoryName,
        Integer stock
) {}
