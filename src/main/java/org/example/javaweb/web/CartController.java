package org.example.javaweb.web;

import lombok.RequiredArgsConstructor;
import org.example.javaweb.service.ProductService;
import org.example.javaweb.web.cart.Cart;
import org.example.javaweb.web.dto.CartUpdateResponseDto;
import org.example.javaweb.web.mapper.CartMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
@SessionAttributes("cart")
public class CartController {

    private final ProductService productService;

    @ModelAttribute("cart")
    public Cart cart() {
        return new Cart();
    }

    @GetMapping
    public String index(@ModelAttribute("cart") Cart cart, Model model) {
        model.addAttribute("items", cart.getItems().stream().map(CartMapper::toDto).toList());
        model.addAttribute("totalQuantity", cart.getTotalQuantity());
        model.addAttribute("totalAmount", cart.getTotalAmount());
        model.addAttribute("isEmpty", cart.isEmpty());
        return "cart/index";
    }

    @PostMapping("/add")
    public String add(@RequestParam Long productId,
                      @RequestParam(defaultValue = "1") int quantity,
                      @ModelAttribute("cart") Cart cart,
                      RedirectAttributes ra) {

        var product = productService.findById(productId);

        if (product.getStock() <= 0) {
            ra.addFlashAttribute("error", "Proizvod trenutno nije dostupan na zalihi.");
            return "redirect:/products/" + productId;
        }

        cart.add(
                product.getId(),
                product.getName(),
                product.getPrice(),
                quantity,
                product.getStock()
        );

        ra.addFlashAttribute("success", "Dodano u košaricu.");
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String update(@RequestParam Long productId,
                         @RequestParam int quantity,
                         @ModelAttribute("cart") Cart cart,
                         RedirectAttributes ra) {

        var product = productService.findById(productId);

        if (product.getStock() <= 0) {
            cart.remove(productId);
            ra.addFlashAttribute("error", "Proizvod više nije dostupan pa je uklonjen iz košarice.");
            return "redirect:/cart";
        }

        cart.setQuantity(productId, quantity, product.getStock());
        ra.addFlashAttribute("success", "Košarica je ažurirana.");
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam Long productId,
                         @ModelAttribute("cart") Cart cart,
                         RedirectAttributes ra) {
        cart.remove(productId);
        ra.addFlashAttribute("success", "Stavka uklonjena iz košarice.");
        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clear(@ModelAttribute("cart") Cart cart, RedirectAttributes ra) {
        cart.clear();
        ra.addFlashAttribute("success", "Košarica je ispražnjena.");
        return "redirect:/cart";
    }

    @PostMapping(
            value = "/update-ajax",
            consumes = "application/x-www-form-urlencoded",
            produces = "application/json"
    )
    public CartUpdateResponseDto updateAjax(@RequestParam Long productId,
                                            @RequestParam int quantity,
                                            @ModelAttribute("cart") Cart cart) {

        var product = productService.findById(productId);

        // ako nema stocka -> makni iz košarice
        if (product.getStock() <= 0) {
            cart.remove(productId);
            return new CartUpdateResponseDto(
                    true,
                    productId,
                    0,
                    BigDecimal.ZERO,
                    cart.getTotalQuantity(),
                    cart.getTotalAmount(),
                    cart.isEmpty(),
                    "Proizvod više nije dostupan pa je uklonjen iz košarice."
            );
        }

        // quantity <=0 => remove, inače clamp na maxStock
        cart.setQuantity(productId, quantity, product.getStock());

        // pronađi stavku nakon update-a (možda je obrisana)
        var itemOpt = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst();

        int finalQty = itemOpt.map(org.example.javaweb.web.cart.CartItem::getQuantity).orElse(0);
        BigDecimal lineTotal = itemOpt.map(org.example.javaweb.web.cart.CartItem::getLineTotal).orElse(BigDecimal.ZERO);

        return new CartUpdateResponseDto(
                true,
                productId,
                finalQty,
                lineTotal,
                cart.getTotalQuantity(),
                cart.getTotalAmount(),
                cart.isEmpty(),
                "Košarica je ažurirana."
        );
    }

}
