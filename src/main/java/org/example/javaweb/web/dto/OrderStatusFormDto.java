package org.example.javaweb.web.dto;

import jakarta.validation.constraints.NotNull;
import org.example.javaweb.domain.OrderStatus;

public class OrderStatusFormDto {

    @NotNull
    private OrderStatus status;

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
