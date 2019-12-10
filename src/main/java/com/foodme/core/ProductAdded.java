package com.foodme.core;

import java.util.Objects;
import java.util.UUID;

public class ProductAdded extends DomainEvent<CartId> {
    private CartId cartId;
    private UUID itemId;
    private ProductId productId;
    private int quantity;

    public CartId getCartId() {
        return cartId;
    }
    public UUID getItemId() {
        return itemId;
    }
    public ProductId getProductId() {
        return productId;
    }
    public int getQuantity() {        return quantity;    }

    public ProductAdded(CartId cartId, UUID itemId, ProductId productId, int quantity, long aggregateVersion) {
        super(cartId, aggregateVersion);
        this.cartId = cartId;
        this.itemId = itemId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public static ProductAdded from(Cart cart, UUID itemId,Product product, int quantity)
    {
        return new ProductAdded(cart.getId(), itemId, product.getId(), quantity, cart.getNextAggregateVersion() + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductAdded that = (ProductAdded) o;
        return quantity == that.quantity &&
                Objects.equals(cartId, that.cartId) &&
                Objects.equals(itemId, that.itemId) &&
                Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, itemId, productId, quantity);
    }

    @Override
    public String toString() {
        return "ProductAdded{" +
                "cartId=" + cartId +
                ", itemId=" + itemId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}


