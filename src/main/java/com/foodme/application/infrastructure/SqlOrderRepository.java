package com.foodme.application.infrastructure;

import com.foodme.core.IDomainEventPublisher;
import com.foodme.core.IOrderRepository;
import com.foodme.core.Order;

public class SqlOrderRepository implements IOrderRepository {
    private final ConnectionInfo connectionInfo;
    private final IDomainEventPublisher eventPublisher;

    public SqlOrderRepository(ConnectionInfo connectionString, IDomainEventPublisher eventPublisher) {
        this.connectionInfo = connectionString;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void save(Order order) {
        throw new UnsupportedOperationException();
    }
}