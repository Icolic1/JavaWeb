package org.example.javaweb.web.dto;

import java.math.BigDecimal;

public record CartItemDto(
        Long productId,
        String name,
        BigDecimal unitPrice,
        int quantity,
        BigDecimal lineTotal
) {}
