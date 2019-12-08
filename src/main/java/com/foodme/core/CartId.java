package com.foodme.core;

import java.util.Objects;
import java.util.UUID;

public class CartId {
    private static final String IdAsStringPrefix = "Shop-";
    private UUID id;

    private CartId(UUID id) {
        this.id = id;
    }

    public CartId(String id) {
        this.id = UUID.fromString(id.startsWith(IdAsStringPrefix) ? id.substring(IdAsStringPrefix.length()) : id);
    }

    public static CartId unique() {
        return new CartId(UUID.randomUUID());
    }

    public UUID getId() {
        return id;
    }

    public String idAsString() {
        return IdAsStringPrefix + id.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartId other = (CartId) o;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CartId{" + "id=" + id + '}';
    }
}


