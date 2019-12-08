package com.foodme.core;

public class Product {
    private ProductId id;
    private String name;
    private double price;

    public ProductId getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }

    public Product(ProductId id, String name, double price) {
        this.price = price;
        this.name = name;
        this.id = id;
    }
}


