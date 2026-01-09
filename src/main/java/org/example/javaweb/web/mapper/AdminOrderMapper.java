package org.example.javaweb.web.mapper;

import org.example.javaweb.domain.Order;
import org.example.javaweb.domain.OrderItem;
import org.example.javaweb.web.dto.AdminOrderDetailsDto;
import org.example.javaweb.web.dto.AdminOrderItemDto;
import org.example.javaweb.web.dto.AdminOrderListDto;

import java.util.List;

public final class AdminOrderMapper {

    private AdminOrderMapper() {}

    public static AdminOrderListDto toListDto(Order o) {
        return new AdminOrderListDto(
                o.getId(),
                o.getCreatedAt(),
                o.getUsername(),
                o.getPaymentMethod(),
                o.getStatus(),
                o.getTotalAmount()
        );
    }

    public static AdminOrderDetailsDto toDetailsDto(Order o) {
        List<AdminOrderItemDto> items = o.getItems().stream()
                .map(AdminOrderMapper::toItemDto)
                .toList();

        return new AdminOrderDetailsDto(
                o.getId(),
                o.getCreatedAt(),
                o.getUsername(),
                o.getPaymentMethod(),
                o.getStatus(),
                o.getTotalAmount(),
                items
        );
    }

    private static AdminOrderItemDto toItemDto(OrderItem i) {
        return new AdminOrderItemDto(
                i.getProductId(),
                i.getProductName(),
                i.getUnitPrice(),
                i.getQuantity(),
                i.getLineTotal()
        );
    }
}
