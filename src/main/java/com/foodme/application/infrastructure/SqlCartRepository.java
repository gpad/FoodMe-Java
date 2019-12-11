package com.foodme.application.infrastructure;

import com.foodme.application.Serialization;
import com.foodme.core.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class SqlCartRepository implements ICartRepository {
    private final IDomainEventPublisher eventPublisher;
    private ConnectionInfo connectionInfo;

    public SqlCartRepository(ConnectionInfo connectionInfo, IDomainEventPublisher eventPublisher) {
        this.connectionInfo = connectionInfo;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void save(Cart cart) throws SQLException {
        String sqlInsertCart = "insert into carts (id) values (?)";
        String sqlInsertItems = "insert into cart_items (id, product_id, description, qty) values (?, ?, ?, ?)";
        String sqlDeleteOldItems = "delete from cart_items where id in (?)";
        String sqlInsertEvents = "insert into events (id, aggregate_id, version, event_type, payload) values (?, ?, ?, ?, to_json(?::json))";

        try (Connection conn = connect();
             PreparedStatement insertCart = conn.prepareStatement(sqlInsertCart);
             PreparedStatement insertItems = conn.prepareStatement(sqlInsertItems);
             PreparedStatement deleteOldItems = conn.prepareStatement(sqlDeleteOldItems);
             PreparedStatement insertEvents = conn.prepareStatement(sqlInsertEvents);
        ) {
            conn.setAutoCommit(false);

            insertCart.setObject(1, cart.getId().getId());
            insertCart.executeUpdate();

            executeInsertItems(insertItems, cart.getItems());

            deleteOldItems.setArray(1, getIdsOf(cart.getItems(), conn));

            executeInsertEvents(insertEvents, cart.getUncommittedEvents());

            conn.commit();
            conn.setAutoCommit(true);

            publishEvent(cart.getUncommittedEvents());
            cart.clearUncommittedEvents();
        } catch (SQLException ex) {
            throw ex;
        }
    }

    private void publishEvent(Collection<DomainEvent<CartId>> uncommittedEvents) {
        uncommittedEvents.forEach(e -> this.eventPublisher.publishAsync(e));
    }

    private void executeInsertEvents(PreparedStatement statement, Collection<DomainEvent<CartId>> events) throws SQLException {
        //id, aggregate_id, version, event_type, payload
        for (DomainEvent<CartId> event : events) {
            statement.setObject(1, event.getEventId());
            statement.setObject(2, event.getAggregateId().getId());
            statement.setLong(3, event.getAggregateVersion());
            statement.setString(4, event.getClass().getName());
            statement.setString(5, Serialization.serialize(event));
            statement.executeUpdate();
        }

    }

    private Array getIdsOf(Collection<CartItem> items, Connection connection) throws SQLException {
        UUID[] ids = items.stream().map(i -> i.getId()).toArray(size -> new UUID[size]);
        return connection.createArrayOf("uuid", ids);
    }

    private void executeInsertItems(PreparedStatement statement, Collection<CartItem> items) throws SQLException {
        for (CartItem item : items) {
            statement.setObject(1, item.getId());
            statement.setObject(2, item.getProductId().getId());
            statement.setString(3, item.getDescription());
            statement.setInt(4, item.getQuantity());
            statement.executeUpdate();
        }
    }

    @Override
    public Cart load(CartId cartId) {
        String SQL = "select event_type, payload from events where aggregate_id = ?";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setObject(1, cartId.getId());
            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            List<DomainEvent<CartId>> events = new ArrayList<>();
            while (resultSet.next()) {
                DomainEvent<CartId> event = createDomainEventFrom(resultSet, metaData);
                events.add(event);
            }
            return Cart.fromEvents(events);
        } catch (SQLException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    private DomainEvent<CartId> createDomainEventFrom(ResultSet resultSet, ResultSetMetaData metaData) throws SQLException, ClassNotFoundException {
        String eventType = resultSet.getString("event_type");
        String payload = resultSet.getString("payload");
        Class type = Class.forName(eventType);
        return (DomainEvent<CartId>)Serialization.deserialize(payload, type);
    }

    private Connection connect() throws SQLException {

        return DriverManager.getConnection(connectionInfo.getConnectionString(), connectionInfo.getUser(), connectionInfo.getPassword());
    }
}
