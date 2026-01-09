package org.example.javaweb.web;

import lombok.RequiredArgsConstructor;
import org.example.javaweb.repository.OrderRepository;
import org.example.javaweb.web.mapper.OrderMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/my/orders")
public class MyOrdersController {

    private final OrderRepository orderRepository;

    @GetMapping
    public String index(Principal principal, Model model) {
        if (principal == null) return "redirect:/login";

        String username = principal.getName();

        var orders = orderRepository.findByUsernameOrderByCreatedAtDesc(username).stream()
                .map(OrderMapper::toListDto)
                .toList();

        model.addAttribute("orders", orders);
        return "orders/index";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Principal principal, Model model) {
        if (principal == null) return "redirect:/login";

        String username = principal.getName();

        var order = orderRepository.findByIdWithItems(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));

        if (!order.getUsername().equals(username)) {
            return "redirect:/access-denied";
        }

        model.addAttribute("order", OrderMapper.toDetailsDto(order));
        return "orders/details";
    }
}
