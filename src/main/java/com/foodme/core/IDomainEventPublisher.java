package com.foodme.core;;

public interface IDomainEventPublisher {
    <T> void publishAsync(T publishedEvent);
}


