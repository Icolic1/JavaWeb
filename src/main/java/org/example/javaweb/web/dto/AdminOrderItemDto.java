package org.example.javaweb.web.dto;

import java.math.BigDecimal;

public record AdminOrderItemDto(
        Long productId,
        String productName,
        BigDecimal unitPrice,
        int quantity,
        BigDecimal lineTotal
) {}
