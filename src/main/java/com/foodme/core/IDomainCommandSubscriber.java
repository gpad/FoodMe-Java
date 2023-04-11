package com.foodme.core;

import java.util.function.Consumer;

public interface IDomainCommandSubscriber {
    <T> void subscribe(Class<T> klass, Consumer<T> consumer);
}
