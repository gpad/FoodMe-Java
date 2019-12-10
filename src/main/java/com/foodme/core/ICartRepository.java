package com.foodme.core;

import java.sql.SQLException;

public interface ICartRepository {
    void save(Cart cart) throws SQLException;
    Cart load(CartId cartId) throws SQLException;
}


