package com.foodme.application.infrastructure;


/*
http://www.postgresqltutorial.com/postgresql-jdbc/insert/
 */

import com.foodme.core.Cart;
import com.foodme.core.CartId;
import com.foodme.core.ICartRepository;
import com.foodme.core.IDomainEventPublisher;

public class SqlCartRepository implements ICartRepository {
    private final String connectionString;
    private final IDomainEventPublisher eventPublisher;

    public SqlCartRepository(String connectionString, IDomainEventPublisher eventPublisher) {
        this.connectionString = connectionString;
        this.eventPublisher = eventPublisher;
    }

//    private final String connectionString = "jdbc:postgresql://localhost:5432/foodme_dev";

    @Override
    public int save(Cart cart) {
        return 0;
    }

    @Override
    public Cart load(CartId cartId) {
//        Connection conn = DriverManager.getConnection(connectionString);
//        Statement statement = conn.createStatement();
//        statement.executeQuery("select * from cart");
        return null;
    }
}
