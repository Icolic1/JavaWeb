package org.example.javaweb.web.dto;

import java.math.BigDecimal;

public record CartUpdateResponseDto(
        boolean ok,
        Long productId,
        int quantity,
        BigDecimal lineTotal,
        int totalQuantity,
        BigDecimal totalAmount,
        boolean isEmpty,
        String message
) {}
