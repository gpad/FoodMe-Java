package com.foodme.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class Cart extends Aggregate<CartId> {
    private final List<CartItem> cartItems = new ArrayList<>();
    private ShopId shopId;

    protected Cart(CartId id, ShopId shopId) {
        this.setId(id);
        this.shopId = shopId;
    }

    protected Cart(Collection<DomainEvent<CartId>> events) {
        apply(events);
    }

    public static Cart createEmptyFor(User user) {
        return new Cart(CartId.unique(), ShopId.unique());
    }

    public static Cart fromEvents(Collection<DomainEvent<CartId>> events) {
        return new Cart(events);
    }

    public ShopId getShopId() {
        return shopId;
    }

    public Collection<CartItem> getItems() {
        return this.cartItems;
    }

    public UUID addProduct(Product product, int quantity) {
        UUID itemId = UUID.randomUUID();
        Emit(ProductAdded.from(this, itemId, product, quantity));
        return itemId;
    }

    public void When(ProductAdded productAdded) {
        this.cartItems.add(
                new CartItem(
                        productAdded.getItemId(),
                        productAdded.getProductId(),
                        "",
                        productAdded.getQuantity()));
    }

}


