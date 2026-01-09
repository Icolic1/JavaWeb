package org.example.javaweb.web.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.javaweb.domain.Order;
import org.example.javaweb.repository.OrderRepository;
import org.example.javaweb.repository.spec.OrderSpecifications;
import org.example.javaweb.service.OrderService;
import org.example.javaweb.web.dto.OrderStatusFormDto;
import org.example.javaweb.web.mapper.AdminOrderMapper;
import org.example.javaweb.web.mapper.OrderStatusFormMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/orders")
public class AdminOrdersController {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @GetMapping
    public String index(@RequestParam(required = false) String username,
                        @RequestParam(required = false) LocalDate from,
                        @RequestParam(required = false) LocalDate to,
                        Model model) {

        Specification<Order> spec = Specification
                .where(OrderSpecifications.usernameContainsIgnoreCase(username))
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

        var orderDto = AdminOrderMapper.toDetailsDto(order);

        // DTO -> DTO (bez entiteta u formi)
        model.addAttribute("order", orderDto);
        model.addAttribute("statusForm", OrderStatusFormMapper.from(orderDto));

        return "admin/orders/details";
    }

    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id,
                               @Valid @ModelAttribute("statusForm") OrderStatusFormDto form,
                               BindingResult bindingResult,
                               RedirectAttributes ra) {

        if (bindingResult.hasErrors()) {
            ra.addFlashAttribute("errorMessage", "Neispravan status.");
            return "redirect:/admin/orders/" + id;
        }

        try {
            orderService.updateStatus(id, form.getStatus());
            ra.addFlashAttribute("successMessage", "Status narudžbe je ažuriran.");
        } catch (IllegalStateException ex) {
            ra.addFlashAttribute("errorMessage", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            ra.addFlashAttribute("errorMessage", "Narudžba nije pronađena.");
        }

        return "redirect:/admin/orders/" + id;
    }
}
