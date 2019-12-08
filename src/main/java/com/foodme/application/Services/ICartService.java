package com.foodme.application.Services;

import com.foodme.core.Cart;

public interface ICartService {
    Cart get(String cartId);

    Cart create();

    void addProduct(String cartId, String productId, int quantity);
}


