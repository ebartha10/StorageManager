package com.example.tp_tema3_fx.backend.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Order model.
 */
public class Order {
    private int id;
    private Client client;
    private Map<Product, Integer> products;

    public Order() {
    }

    public Order(int id, Client client) {
        this.id = id;
        this.client = client;
        this.products = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }
    public void addProduct(Product givenProduct, int givenQuantity){
        this.products.put(givenProduct, givenQuantity);
    }
}
