package com.foodme.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

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

    protected void emit(DomainEvent<TAggregateId> event)
    {
        this.uncommittedEvents.add(event);
        apply(event);
    }

    protected void apply(DomainEvent<TAggregateId> event)
    {
        executeWhen(event);
        this.version = event.getAggregateVersion();
    }

    private void executeWhen(DomainEvent<TAggregateId> event) {
        try {
            Method method = getWhenMethod(this.getClass(), event.getClass());
            method.invoke(this, event);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static final Map<String, Method> whenMethods = new HashMap<String, Method>();

    private Method getWhenMethod(Class<? extends Aggregate> root, Class<? extends DomainEvent> arg) throws NoSuchMethodException {

        String key = getKeyOf(root, arg);
        Method m = whenMethods.get(key);
        if (m == null){
            m = findMethod(root, arg);
            whenMethods.put(key, m);
        }

        return m;
    }

    private Method findMethod(Class<? extends Aggregate> root, Class<? extends DomainEvent> arg) throws NoSuchMethodException {
        try {
            return root.getDeclaredMethod("when", arg);
        } catch (NoSuchMethodException e) {
            return root.getMethod("when", arg);
        }
    }

    private String getKeyOf(Class<? extends Aggregate> root, Class<? extends DomainEvent> arg) {
        return root.getName() + "_" + arg.getName();
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


