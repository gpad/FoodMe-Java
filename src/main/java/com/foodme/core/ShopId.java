package com.foodme.core;

import java.util.Objects;
import java.util.UUID;

public class ShopId {
    private static final String IdAsStringPrefix = "Shop-";
    private UUID id;

    private ShopId(UUID id) {
        this.id = id;
    }

    public ShopId(String id) {
        this.id = UUID.fromString(id.startsWith(IdAsStringPrefix) ? id.substring(IdAsStringPrefix.length()) : id);
    }

    public static ShopId unique() {
        return new ShopId(UUID.randomUUID());
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
        ShopId other = (ShopId) o;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ShopId{" + "id=" + id + '}';
    }
}