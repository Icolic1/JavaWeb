package org.example.javaweb.web.admin;

import lombok.RequiredArgsConstructor;
import org.example.javaweb.domain.Order;
import org.example.javaweb.repository.OrderRepository;
import org.example.javaweb.repository.spec.OrderSpecifications;
import org.example.javaweb.web.mapper.AdminOrderMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/orders")
public class AdminOrdersController {

    private final OrderRepository orderRepository;

    @GetMapping
    public String index(@RequestParam(required = false) String username,
                        @RequestParam(required = false) LocalDate from,
                        @RequestParam(required = false) LocalDate to,
                        Model model) {

        Specification<Order> spec =
                Specification.where(OrderSpecifications.usernameContainsIgnoreCase(username))
                        .and(OrderSpecifications.createdAtFrom(from))
                        .and(OrderSpecifications.createdAtTo(to));

        var orders = orderRepository.findAll(spec).stream()
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .map(AdminOrderMapper::toListDto)
                .toList();

        model.addAttribute("orders", orders);
        model.addAttribute("username", username);
        model.addAttribute("from", from);
        model.addAttribute("to", to);

        return "admin/orders/index";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        var order = orderRepository.findByIdWithItems(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));

        model.addAttribute("order", AdminOrderMapper.toDetailsDto(order));
        return "admin/orders/details";
    }
}
