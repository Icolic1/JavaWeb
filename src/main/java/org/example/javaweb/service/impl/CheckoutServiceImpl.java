package org.example.javaweb.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.javaweb.domain.Order;
import org.example.javaweb.domain.PaymentMethod;
import org.example.javaweb.domain.Product;
import org.example.javaweb.repository.ProductRepository;
import org.example.javaweb.service.CheckoutService;
import org.example.javaweb.service.OrderService;
import org.example.javaweb.service.dto.CreateOrderItemDto;
import org.example.javaweb.web.cart.Cart;
import org.example.javaweb.web.cart.CartItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private final ProductRepository productRepository;
    private final OrderService orderService;

    @Override
    @Transactional
    public Order placeOrder(String username, PaymentMethod method, Cart cart) {
        if (cart == null || cart.isEmpty()) {
            throw new IllegalArgumentException("Košarica je prazna.");
        }

        // 1) Učitaj sve proizvode i validiraj zalihe
        List<CreateOrderItemDto> items = new ArrayList<>();

        for (CartItem ci : cart.getItems()) {
            Product p = productRepository.findById(ci.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Proizvod ne postoji: " + ci.getProductId()));

            if (ci.getQuantity() < 1) {
                throw new IllegalArgumentException("Neispravna količina za proizvod: " + p.getName());
            }

            if (p.getStock() < ci.getQuantity()) {
                throw new IllegalArgumentException(
                        "Nema dovoljno zalihe za '" + p.getName() + "'. Dostupno: " + p.getStock()
                );
            }

            // snapshot iz baze (naziv + cijena u trenutku kupnje)
            items.add(new CreateOrderItemDto(
                    p.getId(),
                    p.getName(),
                    p.getPrice(),
                    ci.getQuantity()
            ));
        }

        // 2) Smanji stock (u istoj transakciji)
        for (CreateOrderItemDto dto : items) {
            Product p = productRepository.findById(dto.productId())
                    .orElseThrow(() -> new IllegalArgumentException("Proizvod ne postoji: " + dto.productId()));

            int newStock = p.getStock() - dto.quantity();
            if (newStock < 0) {
                throw new IllegalArgumentException("Nema dovoljno zalihe za '" + p.getName() + "'.");
            }

            p.setStock(newStock);
            productRepository.save(p);
        }

        // 3) Kreiraj Order
        return orderService.create(username, method, items);
    }
}
