package com.foodme.readmodel;

import com.foodme.core.ShopId;
import java.util.Collection;

public interface IOrderReadModel {
    Collection<Order> getAllFor(ShopId shopId);
}


