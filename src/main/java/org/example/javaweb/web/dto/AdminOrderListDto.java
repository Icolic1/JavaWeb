package org.example.javaweb.web.dto;

import org.example.javaweb.domain.OrderStatus;
import org.example.javaweb.domain.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AdminOrderListDto(
        Long id,
        LocalDateTime createdAt,
        String username,
        PaymentMethod paymentMethod,
        OrderStatus status,
        BigDecimal totalAmount
) {}
