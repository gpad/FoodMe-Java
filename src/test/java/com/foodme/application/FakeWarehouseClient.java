package com.foodme.application;

import com.foodme.core.WarehouseClient;

public class FakeWarehouseClient implements WarehouseClient {
    public boolean wasCalled() {
        return false;
    }
}
