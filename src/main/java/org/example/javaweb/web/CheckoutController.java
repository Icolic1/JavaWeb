package org.example.javaweb.web;

import lombok.RequiredArgsConstructor;
import org.example.javaweb.domain.PaymentMethod;
import org.example.javaweb.service.CheckoutService;
import org.example.javaweb.web.cart.Cart;
import org.example.javaweb.web.mapper.CartMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/checkout")
@SessionAttributes("cart")
public class CheckoutController {

    private final CheckoutService checkoutService;

    @ModelAttribute("cart")
    public Cart cart() {
        return new Cart();
    }

    @GetMapping
    public String checkout(@ModelAttribute("cart") Cart cart, Model model, RedirectAttributes ra) {
        if (cart.isEmpty()) {
            ra.addFlashAttribute("error", "Košarica je prazna.");
            return "redirect:/cart";
        }

        model.addAttribute("items", cart.getItems().stream().map(CartMapper::toDto).toList());
        model.addAttribute("totalQuantity", cart.getTotalQuantity());
        model.addAttribute("totalAmount", cart.getTotalAmount());
        return "checkout/index";
    }

    @PostMapping
    public String placeOrder(@ModelAttribute("cart") Cart cart,
                             Principal principal,
                             RedirectAttributes ra,
                             SessionStatus sessionStatus) {

        if (principal == null) {
            // sigurnosna mreža (realno: Spring Security te neće pustiti do ovdje bez login-a)
            return "redirect:/login";
        }

        if (cart.isEmpty()) {
            ra.addFlashAttribute("error", "Košarica je prazna.");
            return "redirect:/cart";
        }

        try {
            var order = checkoutService.placeOrder(principal.getName(), PaymentMethod.COD, cart);

            // isprazni košaricu nakon uspjeha
            cart.clear();
            // ne završavamo session attribute potpuno, samo ostavljamo praznu košaricu:
            // sessionStatus.setComplete();  // koristi samo ako želiš ukloniti "cart" iz sessiona

            ra.addFlashAttribute("success", "Narudžba je zaprimljena (pouzeće).");
            return "redirect:/checkout/success?orderId=" + order.getId();

        } catch (IllegalArgumentException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
            return "redirect:/checkout";
        }
    }

    @GetMapping("/success")
    public String success(@RequestParam Long orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "checkout/success";
    }
}
