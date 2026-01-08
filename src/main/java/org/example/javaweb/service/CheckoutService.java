package org.example.javaweb.service;

import org.example.javaweb.domain.Order;
import org.example.javaweb.domain.PaymentMethod;
import org.example.javaweb.web.cart.Cart;

public interface CheckoutService {

    /**
     * Kreira narudžbu iz košarice:
     * - validira dostupnost zalihe
     * - smanjuje stock
     * - kreira Order + OrderItem snapshot
     *
     * @throws IllegalArgumentException ako je košarica prazna ili nema dovoljno zalihe
     */
    Order placeOrder(String username, PaymentMethod method, Cart cart);
}
