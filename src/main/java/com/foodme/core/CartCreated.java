package com.foodme.core;

import java.util.UUID;

public class CartCreated extends DomainEvent<CartId> {
    private final ShopId shopId;

    public CartCreated(CartId cartId, ShopId shopId, long aggregateVersion) {
        super(cartId, aggregateVersion);
        this.shopId = shopId;
    }

    public static CartCreated from(CartId id, ShopId shopId) {
        return new CartCreated(id, shopId, 1);
    }

    public ShopId getShopId() {
        return shopId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartCreated that = (CartCreated) o;
        return shopId.equals(that.shopId)
                && this.getAggregateId().equals(that.getAggregateId())
                && this.getAggregateVersion() == that.getAggregateVersion();
    }

    @Override
    public int hashCode() {
        return shopId != null ? shopId.hashCode() : 0;
    }
}
