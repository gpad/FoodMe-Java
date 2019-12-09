package com.foodme.core;

import java.util.UUID;

public abstract class DomainEvent<TAggregateId> {
    private TAggregateId aggregateId;
    private UUID eventId;
    private long aggregateVersion;

    public DomainEvent(TAggregateId aggregateId, long aggregateVersion) {
        this.aggregateId = aggregateId;
        this.aggregateVersion = aggregateVersion;
        this.eventId = UUID.randomUUID();
    }

    public TAggregateId getAggregateId() {
        return aggregateId;
    }

    public UUID getEventId() {
        return eventId;
    }

    private void setEventId(UUID value) {
        eventId = value;
    }
//    public void setAggregateVersion(long value) {
//        aggregateVersion = value;
//    }

    public long getAggregateVersion() {
        return aggregateVersion;
    }
}
