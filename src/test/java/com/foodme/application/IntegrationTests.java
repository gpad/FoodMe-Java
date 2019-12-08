package com.foodme.application;

import com.foodme.application.Migrations.Migrator;
import com.foodme.application.infrastructure.*;
import com.foodme.core.*;
import com.foodme.readmodel.IOrderReadModel;
import com.foodme.readmodel.IProductsReadModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class IntegrationTests {
    private static final String ConnectionString = "Data Source=127.0.0.1,1433;Initial Catalog=FoodMeDev;User ID=SA;Password=StrongPassword1;";
    private User user;
    private Product shampoo = new Product(ProductId.unique(), "shampoo", 12.32);
    private Product soap = new Product(ProductId.unique(), "soap", 3.42);
    private ICartRepository cartRepository;
    private IOrderRepository orderRepository;
    private IProductsReadModel productsReadModel;
    private IOrderReadModel orderReadModel;
    private DomainEventPubSub domainEventPubSub;

    public void dataBaseFixtureOneTimeSetUp() {
        Migrator.migrateDb(ConnectionString);
    }

    @Before
    public void setup() throws Exception {
        deleteTables("carts", "cart_items");
        user = new User(UserId.unique());
        domainEventPubSub = new DomainEventPubSub();
        cartRepository = new SqlCartRepository(ConnectionString, domainEventPubSub);
        orderRepository = new SqlOrderRepository(ConnectionString, domainEventPubSub);
        productsReadModel = new InMemoryProductsReadModel(domainEventPubSub);
        orderReadModel = new InMemoryOrderReadModel();
    }

    @After
    public void tearDown() throws Exception {
        domainEventPubSub.close();
    }

    protected void deleteTables(String... tables) {
        throw new UnsupportedOperationException();
    }

    private com.foodme.readmodel.Product mostSeenProduct(Product product, int quantity) {
        return new com.foodme.readmodel.Product(product.getId(), product.getName(), product.getPrice(), quantity);
    }

    private com.foodme.readmodel.Product readModelOrderFrom(Order order) {
        throw new UnsupportedOperationException();
    }

    @Test
    void addingProductToCartPopulateProductsReadModel() {
        Cart cart = Cart.createEmptyFor(user);
        cart.addProduct(shampoo, 2);
        cart.addProduct(soap, 1);

        cartRepository.save(cart);

        assertThat(productsReadModel.getMostSeen(), equalTo(
                Arrays.asList(
                        mostSeenProduct(shampoo, 2),
                        mostSeenProduct(soap, 1))
        ));
    }

    @Test
    public void checkOutCartPlaceOrderToProperShop(){
        Cart cart = Cart.createEmptyFor(user);
        cart.addProduct(shampoo, 2);
        cart.addProduct(soap, 1);
        cartRepository.save(cart);

        Order order = Order.checkout(cart);
        orderRepository.save(order);

        Collection<com.foodme.readmodel.Order> orders = orderReadModel.getAllFor(cart.getShopId());
        assertThat(orders, equalTo(
                Arrays.asList(
                        readModelOrderFrom(order)
                )
        ));
    }

}


