package com.foodme.application.infrastructure;

import com.foodme.core.IDomainCommandExecutor;
import com.foodme.core.IDomainCommandSubscriber;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;


public class DomainCommandPubSub implements IDomainCommandSubscriber, IDomainCommandExecutor {
    Map<Class, Consumer<?>> handlers = new HashMap();

    @Override
    public <T> void subscribe(Class<T> klass, Consumer<T> consumer) {
        addHandlerFor(klass, consumer);
    }

    @Override
    public <T> void execute(T command) {
        Class eventType = command.getClass();
        Consumer<T> consumer = (Consumer<T>) getConsumerFor(eventType);
        if (consumer == null) throw new RuntimeException("Unable to find command for " + eventType);
        consumer.accept(command);
    }

    private Consumer<?> getConsumerFor(Class commandType) {
        return handlers.get(commandType);
    }

    private <T> void addHandlerFor(Class<T> klass, Consumer<T> consumer) {
        Consumer<?> consumers = this.handlers.get(klass);
        if (consumers != null) {
            throw new RuntimeException("Only one consumer for command");
        }
        this.handlers.put(klass, consumer);
    }
}
