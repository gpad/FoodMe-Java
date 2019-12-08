package com.foodme.core;

import java.util.function.Consumer;

public interface IDomainEventSubscriber {
    <T> void subscribe(Consumer<T> consumer);
}


