package com.foodme.application.infrastructure;

import com.foodme.Main;
import com.foodme.core.*;
import org.flywaydb.core.Flyway;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class SqlCartRepositoryTest {
    private User user;
    private DomainEventPubSub domainEventPubSub;
    private SqlCartRepository cartRepository;
    private Product shampoo = new Product(ProductId.unique(), "shampoo", 12.32);
    private Product soap = new Product(ProductId.unique(), "soap", 3.42);
    private static ConnectionInfo connectionInfo = Main.CONNECTION_INFO;

    @BeforeClass
    public static void migrateDb(){
        Flyway flyway = Flyway.configure().dataSource(connectionInfo.getConnectionString(), connectionInfo.getUser(), connectionInfo.getPassword()).load();
        flyway.migrate();
    }

    @Before
    public void setUp() throws Exception {
        deleteTables("carts", "cart_items");
        user = new User(UserId.unique());
        domainEventPubSub = new DomainEventPubSub();
        cartRepository = new SqlCartRepository(connectionInfo, domainEventPubSub);
    }

    private void deleteTables(String... tables) {
        for (String table : tables) {
            deleteTable(table);
        }
    }

    private void deleteTable(String table) {
        try (Connection conn = DriverManager.getConnection(connectionInfo.getConnectionString(), connectionInfo.getUser(), connectionInfo.getPassword());
             PreparedStatement statement = conn.prepareStatement("delete from " + table)) {
            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @After
    public void tearDown() throws Exception {
        domainEventPubSub.close();
    }

    @Test
    public void storeAndLoadCart() throws SQLException {
        Cart cart = Cart.createEmptyFor(user);
        cart.addProduct(shampoo, 2);
        cart.addProduct(soap, 1);

        cartRepository.save(cart);
        Cart loadedCart = cartRepository.load(cart.getId());

        assertThat(cart.getItems(), equalTo(loadedCart.getItems()));
        assertThat(cart.getNextAggregateVersion(), equalTo(loadedCart.getNextAggregateVersion()));
        assertThat(cart.getId(), equalTo(loadedCart.getId()));
    }

    @Test
    public void saveUpdatedCart() throws SQLException {
        Cart cart = Cart.createEmptyFor(user);
        cart.addProduct(shampoo, 2);

        cartRepository.save(cart);
        cart.addProduct(soap, 1);
        cartRepository.save(cart);

        Cart loadedCart = cartRepository.load(cart.getId());
        assertThat(loadedCart.getItems(), equalTo(cart.getItems()));
    }
}