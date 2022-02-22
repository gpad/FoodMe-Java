package com.foodme.application;

import com.foodme.Main;
import com.foodme.application.infrastructure.*;
import com.foodme.core.*;
import com.foodme.readmodel.IOrderReadModel;
import com.foodme.readmodel.IProductsReadModel;
import org.flywaydb.core.Flyway;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class IntegrationTests {
    private static ConnectionInfo connectionInfo = Main.CONNECTION_INFO;
    private User user;
    private Product shampoo = new Product(ProductId.unique(), "shampoo", 12.32);
    private Product soap = new Product(ProductId.unique(), "soap", 3.42);
    private ICartRepository cartRepository;
    private IOrderRepository orderRepository;
    private IProductsReadModel productsReadModel;
    private IOrderReadModel orderReadModel;
    private DomainEventPubSub domainEventPubSub;
    private WarehouseUpdater warehouseUpdater;
    private FakeWarehouseClient fakeWarehouseClient;

    @BeforeClass
    public static void migrateDb() {
        Flyway flyway = Flyway.configure().dataSource(connectionInfo.getConnectionString(), connectionInfo.getUser(), connectionInfo.getPassword()).load();
        flyway.migrate();
    }

    @Before
    public void setup() throws Exception {
        deleteTables("carts", "cart_items", "events");
        user = new User(UserId.unique());
        domainEventPubSub = new DomainEventPubSub();
        cartRepository = new SqlCartRepository(Main.CONNECTION_INFO, domainEventPubSub);
        orderRepository = new SqlOrderRepository(connectionInfo, domainEventPubSub);
        productsReadModel = new InMemoryProductsReadModel(domainEventPubSub);
        orderReadModel = new InMemoryOrderReadModel();
        fakeWarehouseClient = new FakeWarehouseClient();
        warehouseUpdater = new WarehouseUpdater(fakeWarehouseClient);
        warehouseUpdater.start(domainEventPubSub);
    }

    @After
    public void tearDown() throws InterruptedException {
        domainEventPubSub.close();
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

    private com.foodme.readmodel.Product mostSeenProduct(Product product, int quantity) {
        return new com.foodme.readmodel.Product(product.getId(), product.getName(), product.getPrice(), quantity);
    }

    private com.foodme.readmodel.Product readModelOrderFrom(Order order) {
        throw new UnsupportedOperationException();
    }

    @Test
    public void addingProductToCartPopulateProductsReadModel() throws SQLException, InterruptedException {
        Cart cart = Cart.createEmptyFor(user);
        cart.addProduct(shampoo, 2);
        cart.addProduct(soap, 1);

        cartRepository.save(cart);

        Thread.sleep(100);
        assertThat(productsReadModel.getMostSeen(), equalTo(
                Arrays.asList(mostSeenProduct(shampoo, 2), mostSeenProduct(soap, 1))
        ));
    }

    @Test
    public void checkOutCartPlaceOrderToProperShop() throws SQLException {
        Cart cart = Cart.createEmptyFor(user);
        cart.addProduct(shampoo, 2);
        cart.addProduct(soap, 1);
        cartRepository.save(cart);

        Order order = Order.checkout(cart);
        orderRepository.save(order);

        Collection<com.foodme.readmodel.Order> orders = orderReadModel.getAllFor(cart.getShopId());
        assertThat(orders, equalTo(Arrays.asList(readModelOrderFrom(order))));
    }

    @Test
    public void checkOutCartSubmitAnOrderToWarehouse() throws SQLException {
        Cart cart = Cart.createEmptyFor(user);
        cart.addProduct(shampoo, 2);
        cart.addProduct(soap, 1);
        cartRepository.save(cart);

        Order order = Order.checkout(cart);
        orderRepository.save(order);

        assertThat(fakeWarehouseClient.wasCalled(), is(true));
    }

}


