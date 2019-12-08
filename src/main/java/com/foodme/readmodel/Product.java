package com.foodme.readmodel;

import com.foodme.core.ProductId;
import java.util.Objects;

public class Product {
    private ProductId id;
    private String name;
    private double price;
    private long quantity;

    public Product(ProductId id, String name, double price, long quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    public ProductId getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public long getQuantity() {
        return quantity;
    }

    public Product add(int quantity){
        return new Product(getId(), getName(), getPrice(), getQuantity() + quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 &&
                quantity == product.quantity &&
                Objects.equals(id, product.id) &&
                Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, quantity);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}


