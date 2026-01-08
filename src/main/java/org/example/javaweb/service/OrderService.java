package org.example.javaweb.service;

import org.example.javaweb.domain.Order;
import org.example.javaweb.domain.PaymentMethod;
import org.example.javaweb.service.dto.CreateOrderItemDto;

import java.util.List;

public interface OrderService {

    Order create(String username, PaymentMethod method, List<CreateOrderItemDto> items);

    List<Order> findForUser(String username);

    List<Order> findAll();

    Order findById(Long id);
}
