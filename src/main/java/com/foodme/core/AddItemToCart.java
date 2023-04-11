package com.foodme.core;

public class AddItemToCart {
    private final CartId id;
    private final Product product;
    private final int quantity;

    public AddItemToCart(CartId id, Product product, int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public CartId getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
