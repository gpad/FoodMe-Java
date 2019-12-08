package com.foodme.application.infrastructure;

import com.foodme.core.IDomainEventPublisher;
import com.foodme.core.IDomainEventSubscriber;

import java.util.function.Consumer;

public class DomainEventPubSub implements IDomainEventSubscriber, IDomainEventPublisher {
    @Override
    public <T> void publishAsync(T publishedEvent) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> void subscribe(Consumer<T> consumer) {
        throw new UnsupportedOperationException();
    }

    public void close() {
        throw new UnsupportedOperationException();
    }
}
