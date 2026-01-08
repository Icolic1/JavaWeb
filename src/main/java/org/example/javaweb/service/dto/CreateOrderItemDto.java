package org.example.javaweb.service.dto;

import java.math.BigDecimal;

public record CreateOrderItemDto(
        Long productId,
        String productName,
        BigDecimal unitPrice,
        int quantity
) {}
