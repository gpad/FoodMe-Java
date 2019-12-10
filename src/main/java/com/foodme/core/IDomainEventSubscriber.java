package com.foodme.core;

import java.util.function.Consumer;

public interface IDomainEventSubscriber {
    <T> void subscribe(Class<T> klass, Consumer<T> consumer);
}


