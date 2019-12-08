package com.foodme.core;

import java.util.Objects;
import java.util.UUID;

public class CartItem {
    private UUID id;
    private ProductId productId;
    private int quantity;
    private String description;

    public CartItem(UUID id, ProductId productId, String name, int quantity) {
        this.id = id;
        this.description = name;
        this.productId = productId;
        this.quantity = quantity;
    }

    public UUID getId() {
        return id;
    }

    public ProductId getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return quantity == cartItem.quantity &&
                Objects.equals(id, cartItem.id) &&
                Objects.equals(productId, cartItem.productId) &&
                Objects.equals(description, cartItem.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, quantity, description);
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", description='" + description + '\'' +
                '}';
    }
}


