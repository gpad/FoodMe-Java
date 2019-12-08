package com.foodme.core;

public interface ICartRepository {
    int save(Cart cart);
    Cart load(CartId cartId);
}


