package com.foodme;

import com.foodme.application.Services.ICartService;
import com.foodme.application.Services.IOrderService;
import com.foodme.application.infrastructure.DomainEventPubSub;
import com.foodme.core.Cart;
import com.foodme.core.WarehouseUpdater;
import org.flywaydb.core.Flyway;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

public class Main {

    private static String connectionString;


    public static void main(String[] args) {
        System.out.println("Hello World!");
        migrateDb();
        DomainEventPubSub domainEventPubSub = new DomainEventPubSub();
        Policies policies = startupPolicies();
        if (args.length == 1 && args[0] == "createCart") {
            ICartService cartService = createCartService(connectionString, domainEventPubSub);
            Cart cart = cartService.create();
            System.out.println("Created new cart " + cart.getId());
            return;
        }
        if (args.length == 4 && args[0] == "addProduct") {
            ICartService cartService = createCartService(connectionString, domainEventPubSub);
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
        if (args.length == 2 && args[0] == "checkout") {
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

    private static ICartService createCartService(String connectionString, DomainEventPubSub domainEventPubSub) {
        return null;
    }

    private static Policies startupPolicies() {
        Policies policies = new Policies();
        policies.add(Arrays.asList(new WarehouseUpdater()));
        policies.start();
        return policies;
    }

    private static void migrateDb() {
        Flyway flyway = Flyway.configure().dataSource("jdbc:postgresql://localhost:5433/food_me_dev", "food_me_user", "food_me_pwd").load();
        flyway.migrate();
    }
}
