package com.foodme.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Aggregate<TAggregateId> {
    public static final long NewAggregateVersion = -1;
    private final List<DomainEvent<TAggregateId>> uncommittedEvents = new ArrayList<>();
    private long version = NewAggregateVersion;
    private TAggregateId id;

    public long getNextAggregateVersion() {
        return version + 1;
    }

    public TAggregateId getId() {
        return id;
    }

    protected void setId(TAggregateId id) {
        this.id = id;
    }

    protected void Emit(DomainEvent<TAggregateId> event)
    {
        this.uncommittedEvents.add(event);
        apply(event);
    }

    protected void apply(DomainEvent<TAggregateId> event)
    {
        // TODO: 07/12/19 apply event to aggregate!!!
//        ((dynamic)this).When((dynamic)@event);
        this.version = event.getAggregateVersion();
    }
    protected void apply(Collection<DomainEvent<TAggregateId>> events)
    {
        events.forEach(event -> apply(event));
    }

    public Collection<DomainEvent<TAggregateId>> getUncommittedEvents()
    {
        return this.uncommittedEvents;
    }

    public void clearUncommittedEvents()
    {
        this.uncommittedEvents.clear();
    }
}


