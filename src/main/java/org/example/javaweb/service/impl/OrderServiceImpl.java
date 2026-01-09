package org.example.javaweb.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.javaweb.domain.Order;
import org.example.javaweb.domain.OrderItem;
import org.example.javaweb.domain.OrderStatus;
import org.example.javaweb.domain.PaymentMethod;
import org.example.javaweb.repository.OrderRepository;
import org.example.javaweb.service.OrderService;
import org.example.javaweb.service.dto.CreateOrderItemDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public Order create(String username, PaymentMethod method, List<CreateOrderItemDto> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item.");
        }

        Order order = Order.builder()
                .username(username)
                .paymentMethod(method)
                .build();

        BigDecimal total = BigDecimal.ZERO;

        for (CreateOrderItemDto dto : items) {
            if (dto.quantity() < 1) {
                throw new IllegalArgumentException("Quantity must be >= 1 for productId=" + dto.productId());
            }

            OrderItem oi = OrderItem.builder()
                    .productId(dto.productId())
                    .productName(dto.productName())
                    .unitPrice(dto.unitPrice())
                    .quantity(dto.quantity())
                    .build();

            order.addItem(oi);
            total = total.add(oi.getLineTotal());
        }

        order.setTotalAmount(total);
        return orderRepository.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findForUser(String username) {
        return orderRepository.findByUsernameOrderByCreatedAtDesc(username);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return orderRepository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    @Transactional(readOnly = true)
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
    }

    @Override
    @Transactional
    public Order updateStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));

        OrderStatus current = order.getStatus();

        if (current == newStatus) {
            return order; // no-op
        }

        if (!isAllowedTransition(current, newStatus)) {
            throw new IllegalStateException(
                    "Invalid status transition: " + current + " -> " + newStatus
            );
        }

        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    private boolean isAllowedTransition(OrderStatus from, OrderStatus to) {
        // Minimalna state machine logika (proširi po potrebi)
        return switch (from) {
            case CREATED -> (to == OrderStatus.PAID || to == OrderStatus.CANCELLED);
            case PAID -> (to == OrderStatus.SHIPPED || to == OrderStatus.CANCELLED);
            case SHIPPED -> false;     // nakon slanja nema promjena
            case CANCELLED -> false;   // otkazano je terminalno
        };
    }
}
