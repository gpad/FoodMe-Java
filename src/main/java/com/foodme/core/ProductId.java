package com.foodme.core;

import java.util.Objects;
import java.util.UUID;

public class ProductId {
    private static final String IdAsStringPrefix = "Product-";
    private UUID id;

    private ProductId(UUID id) {
        this.id = id;
    }

    public ProductId(String id) {
        this.id = UUID.fromString(id.startsWith(IdAsStringPrefix) ? id.substring(IdAsStringPrefix.length()) : id);
    }

    public static ProductId unique() {
        return new ProductId(UUID.randomUUID());
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
        ProductId other = (ProductId) o;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ProductId{" + "id=" + id + '}';
    }

}
