package com.foodme.application.infrastructure;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import com.foodme.core.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SqlCartRepositoryTest {
    private User user;
    private DomainEventPubSub domainEventPubSub;
    private final String connectionString = "jdbc:postgresql://localhost:5432/foodme_dev";
    private SqlCartRepository cartRepository;
    private Product shampoo = new Product(ProductId.unique(), "shampoo", 12.32);
    private Product soap = new Product(ProductId.unique(), "soap", 3.42);

    @Before
    public void setUp() throws Exception {
        deleteTables("carts", "cart_items");
        user = new User(UserId.unique());
        domainEventPubSub = new DomainEventPubSub();
        cartRepository = new SqlCartRepository(connectionString, domainEventPubSub);
    }

    private void deleteTables(String... tables) {
        throw new UnsupportedOperationException();
    }

    @After
    public void tearDown() throws Exception {
        domainEventPubSub.close();
    }

    @Test
    public  void storeAndLoadCart()
    {
        Cart cart = Cart.createEmptyFor(user);
        cart.addProduct(shampoo, 2);
        cart.addProduct(soap, 1);

        cartRepository.save(cart);
        Cart loadedCart = cartRepository.load(cart.getId());

        assertThat(cart.getItems(), equalTo(loadedCart.getItems()));
        assertThat(cart.getNextAggregateVersion(), equalTo(loadedCart.getNextAggregateVersion()));
        assertThat(cart.getId(), equalTo(loadedCart.getId()));
    }
}