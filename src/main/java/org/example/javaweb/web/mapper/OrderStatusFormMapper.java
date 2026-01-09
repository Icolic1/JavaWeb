package org.example.javaweb.web.mapper;

import org.example.javaweb.web.dto.AdminOrderDetailsDto;
import org.example.javaweb.web.dto.OrderStatusFormDto;

public final class OrderStatusFormMapper {

    private OrderStatusFormMapper() {}

    public static OrderStatusFormDto from(AdminOrderDetailsDto orderDto) {
        OrderStatusFormDto form = new OrderStatusFormDto();
        form.setStatus(orderDto.status());
        return form;
    }
}
