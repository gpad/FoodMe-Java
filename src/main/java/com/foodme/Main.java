package com.foodme;

import com.foodme.application.Services.ICartService;
import com.foodme.application.Services.IOrderService;
import com.foodme.application.infrastructure.ConnectionInfo;
import com.foodme.application.infrastructure.DomainEventPubSub;
import com.foodme.application.infrastructure.SqlCartRepository;
import com.foodme.core.Cart;
import com.foodme.core.WarehouseUpdater;
import org.flywaydb.core.Flyway;

import java.util.Arrays;

public class Main {

    public static final ConnectionInfo CONNECTION_INFO = new ConnectionInfo("jdbc:postgresql://localhost:5433/food_me_dev", "food_me_user", "food_me_pwd");

    public static void main(String[] args) {
        System.out.println("Hello World!");
        migrateDb(CONNECTION_INFO);
        DomainEventPubSub domainEventPubSub = new DomainEventPubSub();
        Policies policies = startupPolicies();
        if (args.length == 1 && args[0].equals("createCart")) {
            ICartService cartService = createCartService(CONNECTION_INFO, domainEventPubSub);
            Cart cart = cartService.create();
            System.out.println("Created new cart " + cart.getId());
            return;
        }
        if (args.length == 4 && args[0].equals("addProduct")) {
            ICartService cartService = createCartService(CONNECTION_INFO, domainEventPubSub);
            String cartId = args[1];
            String productId = args[2];
            int quantity = Integer.parseInt(args[3]);
            Cart cart = cartService.get(cartId);
            if (cart == null) {
                System.out.println("Cart not Found!!!");
                System.exit(-1);
                return;
            }
            cartService.addProduct(cartId, productId, quantity);
            System.out.println("Product added!!!");
            return;
        }
        if (args.length == 2 && args[0].equals("checkout")) {
            String cartId = args[1];
            IOrderService orderService = createOrderService();
            orderService.checkout(cartId);
            return;
        }
        System.out.println("No command to execute!!! Call:");
        System.out.println("addProduct <cartId> <productId> <quantity>");
        System.out.println("checkout <cartId>");
        System.exit(-1);
        return;
    }

    private static IOrderService createOrderService() {
        return null;
    }

    private static ICartService createCartService(ConnectionInfo connectionInfo, DomainEventPubSub domainEventPubSub) {
        SqlCartRepository cartRepository = new SqlCartRepository(connectionInfo, domainEventPubSub);
        return new ConsoleCartService(cartRepository);
    }

    private static Policies startupPolicies() {
        Policies policies = new Policies();
        policies.add(Arrays.asList(new WarehouseUpdater()));
        policies.start();
        return policies;
    }

    private static void migrateDb(ConnectionInfo connectionInfo) {
        Flyway flyway = Flyway.configure().dataSource(connectionInfo.getConnectionString(), connectionInfo.getUser(), connectionInfo.getPassword()).load();
        flyway.migrate();
    }
}
