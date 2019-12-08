package com.foodme.application.infrastructure;

import com.foodme.core.IDomainEventPublisher;
import com.foodme.core.IOrderRepository;
import com.foodme.core.Order;

public class SqlOrderRepository implements IOrderRepository {
    private final String connectionString;
    private final IDomainEventPublisher eventPublisher;

    public SqlOrderRepository(String connectionString, IDomainEventPublisher eventPublisher) {
        this.connectionString = connectionString;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void save(Order order) {
        throw new UnsupportedOperationException();

    }
//    private String connectionString;
//    private final IDomainEventPublisher publisher;
//
//    public SqlOrderRepository(String connectionString, IDomainEventPublisher publisher) throws Exception {
//        this.connectionString = connectionString;
//        this.publisher = publisher;
//    }
//
//    public async Task = new async();
//
//    SaveAsync(Order order) throws Exception {
//        /* [UNSUPPORTED] 'var' as type is unsupported "var" */
//        sqlConnection = new SqlConnection(connectionString);
//        try {
//            {
//                await;
//                sqlConnection.OpenAsync();
//            }
//        } finally {
//            if (sqlConnection != null)
//                Disposable.mkDisposable(sqlConnection).dispose();
//
//        }
//    }
//
//    @Override
//    public void save(Order order) throws Exception {
//
//    }
}


// await sqlConnection.ExecuteAsync(
//     "insert into order (Id) values (@Id)",
//     new { Id = order.Id });