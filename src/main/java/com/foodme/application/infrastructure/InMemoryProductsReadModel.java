package com.foodme.application.infrastructure;

import com.foodme.core.IDomainEventSubscriber;
import com.foodme.core.ProductAdded;
import com.foodme.core.ProductId;
import com.foodme.readmodel.IProductsReadModel;
import com.foodme.readmodel.Product;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class InMemoryProductsReadModel implements IProductsReadModel {
    private final IDomainEventSubscriber subscriber;
    private final Map<ProductId, Product> mostSeenProducts = new HashMap<>();

    public InMemoryProductsReadModel(IDomainEventSubscriber subscriber) throws Exception {
        this.subscriber = subscriber;
        this.subscriber.<ProductAdded>subscribe(o -> handleProductAddedEvent(o));
    }

    private void handleProductAddedEvent(ProductAdded productAdded) {
        Product zeroProduct = new Product(productAdded.getProductId(), "", 0.0, 0);
        Product entry = this.mostSeenProducts.getOrDefault(productAdded.getProductId(), zeroProduct);
        entry.add(productAdded.getQuantity());
        this.mostSeenProducts.put(productAdded.getProductId(), entry);
    }

    public Collection<Product> getMostSeen() {
        return this.mostSeenProducts.values().stream().sorted(Comparator.comparing(Product::getQuantity)).collect(Collectors.toList());
    }

}

