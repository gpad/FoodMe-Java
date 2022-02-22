package com.foodme.core;

public class WarehouseUpdater extends Policy {
    private final WarehouseClient warehouseClient;

    public WarehouseUpdater(WarehouseClient warehouseClient) {
        this.warehouseClient = warehouseClient;
    }

    public void start(IDomainEventSubscriber subscriber) {
        subscriber.subscribe(OrderSubmittedEvent.class, e -> handleOrderSubmittedEvent(e));
    }

    private void handleOrderSubmittedEvent(OrderSubmittedEvent orderSubmittedEvent) {
        // this.warehouseClient.prepareShippingFor(orderSubmittedEvent.)
        throw new UnsupportedOperationException();
    }
}

