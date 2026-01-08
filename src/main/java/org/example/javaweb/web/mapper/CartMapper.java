package org.example.javaweb.web.mapper;

import org.example.javaweb.web.cart.CartItem;
import org.example.javaweb.web.dto.CartItemDto;

public final class CartMapper {

    private CartMapper() {}

    public static CartItemDto toDto(CartItem item) {
        return new CartItemDto(
                item.getProductId(),
                item.getName(),
                item.getUnitPrice(),
                item.getQuantity(),
                item.getLineTotal()
        );
    }
}
