package com.foodme.core;

import java.util.Objects;
import java.util.UUID;

public class UserId {
    private UUID id;

    private UserId(UUID id) {
        this.id = id;
    }

    public static UserId unique() {
        return new UserId(UUID.randomUUID());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserId userId = (UserId) o;
        return Objects.equals(id, userId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


