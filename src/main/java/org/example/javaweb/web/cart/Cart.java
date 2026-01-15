package org.example.javaweb.web.cart;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {

    private final Map<Long, CartItem> items = new LinkedHashMap<>();

    public Collection<CartItem> getItems() {
        return items.values();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int getTotalQuantity() {
        return items.values().stream().mapToInt(CartItem::getQuantity).sum();
    }

    public BigDecimal getTotalAmount() {
        return items.values().stream()
                .map(CartItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void add(Long productId, String name, BigDecimal unitPrice, int quantity, int maxStock) {
        int qty = normalizeQty(quantity);

        CartItem existing = items.get(productId);
        if (existing == null) {
            int finalQty = Math.min(qty, maxStock);
            items.put(productId, new CartItem(productId, name, unitPrice, finalQty));
            return;
        }

        int newQty = existing.getQuantity() + qty;
        existing.setQuantity(Math.min(newQty, maxStock));
    }

    public void setQuantity(Long productId, int quantity, int maxStock) {
        CartItem existing = items.get(productId);
        if (existing == null) return;

        if (quantity <= 0) {
            items.remove(productId);
            return;
        }

        int qty = Math.min(quantity, maxStock);
        existing.setQuantity(qty);
    }


    public void remove(Long productId) {
        items.remove(productId);
    }

    public void clear() {
        items.clear();
    }

    private int normalizeQty(int q) {
        if (q < 1) return 1;
        return q;
    }
}
