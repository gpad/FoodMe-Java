package com.foodme.application.infrastructure;

import com.foodme.core.IDomainEventPublisher;
import com.foodme.core.IDomainEventSubscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class DomainEventPubSub extends Thread implements IDomainEventSubscriber, IDomainEventPublisher {

    List unpublishedEvents = new ArrayList();
    Map<Class, List<Consumer<?>>> subscribers = new HashMap();
    private boolean exit = false;

    public DomainEventPubSub() {
        this.setDaemon(true);
        this.start();
    }

    @Override
    public void run() {
        while (!exit) {
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                exit = true;
            }
            publishMessages();
        }
    }

    private synchronized void publishMessages() {
        for (Object event : unpublishedEvents) {
            Class eventType = event.getClass();
            List<Consumer<?>> consumers = getConsumersFor(eventType);
            publishMessageTo(event, consumers);
        }
        unpublishedEvents.clear();
    }

    private void publishMessageTo(Object event, List<Consumer<?>> consumers) {
        for (Consumer c : consumers) {
            c.accept(event);
        }
    }

    private List<Consumer<?>> getConsumersFor(Class eventType) {
        List<Consumer<?>> consumers = subscribers.get(eventType);
        return consumers == null ? new ArrayList<Consumer<?>>() : consumers;
    }

    @Override
    public synchronized <T> void publishAsync(T publishedEvent) {
        unpublishedEvents.add(publishedEvent);
    }

    @Override
    public synchronized <T> void subscribe(Class<T> klass, Consumer<T> consumer) {
        addConsumerFor(klass, consumer);
    }

    private <T> void addConsumerFor(Class<T> klass, Consumer<T> consumer) {
        List<Consumer<?>> consumers = this.subscribers.get(klass);
        if (consumers == null) {
            consumers = new ArrayList<>();
            this.subscribers.put(klass, consumers);
        }
        consumers.add(consumer);
    }

    public void close() throws InterruptedException {
        this.exit = true;
        this.join(500);
    }
}
