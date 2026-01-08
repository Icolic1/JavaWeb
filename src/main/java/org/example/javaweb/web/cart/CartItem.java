package org.example.javaweb.web.cart;

import java.math.BigDecimal;

public class CartItem {

    private final Long productId;
    private final String name;
    private final BigDecimal unitPrice;
    private int quantity;

    public CartItem(Long productId, String name, BigDecimal unitPrice, int quantity) {
        this.productId = productId;
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public Long getProductId() { return productId; }
    public String getName() { return name; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getLineTotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
