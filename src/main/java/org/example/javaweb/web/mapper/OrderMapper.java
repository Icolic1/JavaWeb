package org.example.javaweb.web.mapper;

import org.example.javaweb.domain.Order;
import org.example.javaweb.domain.OrderItem;
import org.example.javaweb.web.dto.OrderDetailsDto;
import org.example.javaweb.web.dto.OrderItemDto;
import org.example.javaweb.web.dto.OrderListDto;

import java.util.List;

public final class OrderMapper {

    private OrderMapper() {}

    public static OrderListDto toListDto(Order o) {
        return new OrderListDto(
                o.getId(),
                o.getCreatedAt(),
                o.getPaymentMethod(),
                o.getStatus(),
                o.getTotalAmount()
        );
    }

    public static OrderDetailsDto toDetailsDto(Order o) {
        List<OrderItemDto> items = o.getItems().stream()
                .map(OrderMapper::toItemDto)
                .toList();

        return new OrderDetailsDto(
                o.getId(),
                o.getCreatedAt(),
                o.getPaymentMethod(),
                o.getStatus(),
                o.getTotalAmount(),
                items
        );
    }

    private static OrderItemDto toItemDto(OrderItem i) {
        return new OrderItemDto(
                i.getProductId(),
                i.getProductName(),
                i.getUnitPrice(),
                i.getQuantity(),
                i.getLineTotal()
        );
    }
}
