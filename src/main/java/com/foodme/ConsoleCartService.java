package com.foodme;

import com.foodme.application.Services.ICartService;
import com.foodme.core.*;

import java.sql.SQLException;

public class ConsoleCartService implements ICartService {
    private ICartRepository cartRepository;

    public ConsoleCartService(ICartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart get(String cartId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Cart create() {
        try {
            Cart cart = Cart.createEmptyFor(new User(UserId.unique()));
            cartRepository.save(cart);
            return cart;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addProduct(String cartId, String productId, int quantity) {
        try {
            Cart cart = null;
            cart = cartRepository.load(new CartId(cartId));
            // var product = productRepository.LoadAsync(productId);
            // cart.AddProduct(product, quantity);
            cartRepository.save(cart);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
