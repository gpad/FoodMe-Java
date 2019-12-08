package com.foodme.application.infrastructure;

import com.foodme.core.ShopId;
import com.foodme.readmodel.IOrderReadModel;
import com.foodme.readmodel.Order;

import java.util.Collection;

public class InMemoryOrderReadModel implements IOrderReadModel {

    @Override
    public Collection<Order> getAllFor(ShopId shopId) {
        throw new UnsupportedOperationException();
    }
}


