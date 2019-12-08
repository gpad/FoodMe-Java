package com.foodme.core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class CartTest {

    private User user;
    private Product shampoo = new Product(ProductId.unique(), "shampoo", 12.32);
    private Product soap = new Product(ProductId.unique(), "soap", 3.42);

    @Before
    public void setUp() throws Exception {
        user = new User(UserId.unique());
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void storingCartAggregateEmitEvent() {
        Cart cart = Cart.createEmptyFor(user);

        UUID itemId1 = cart.addProduct(shampoo, 1);
        UUID itemId2 = cart.addProduct(soap, 1);

        assertThat(cart.getUncommittedEvents(), equalTo(
                Arrays.asList(
                        ProductAdded.from(cart, itemId1, shampoo, 1),
                        ProductAdded.from(cart, itemId2, soap, 1))));
    }

    @Test
    public void addProductToCartIncreaseCartItem() {
        Cart cart = Cart.createEmptyFor(user);

        cart.addProduct(shampoo, 1);
        cart.addProduct(soap, 1);

        assertThat(cart.getItems(), is(not(empty())));
    }
}