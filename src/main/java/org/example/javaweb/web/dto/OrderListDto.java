package org.example.javaweb.web.dto;

import org.example.javaweb.domain.OrderStatus;
import org.example.javaweb.domain.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderListDto(
        Long id,
        LocalDateTime createdAt,
        PaymentMethod paymentMethod,
        OrderStatus status,
        BigDecimal totalAmount
) {}
