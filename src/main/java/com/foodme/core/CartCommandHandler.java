package com.foodme.core;

import java.sql.SQLException;

public class CartCommandHandler {
    private ICartRepository repository;

    public CartCommandHandler(ICartRepository repository) {
        this.repository = repository;
    }

    public void subscribe(IDomainCommandSubscriber subscriber) {
        subscriber.subscribe(AddItemToCart.class, cmd -> addItem(cmd));
    }

    private void addItem(AddItemToCart cmd)  {
        try {
            Cart cart = this.repository.load(cmd.getId());
            cart.addProduct(cmd.getProduct(), cmd.getQuantity());
            this.repository.save(cart);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
