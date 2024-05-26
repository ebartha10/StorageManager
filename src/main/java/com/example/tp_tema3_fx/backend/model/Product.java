package com.example.tp_tema3_fx.backend.model;

/**
 * Product model.
 */
public class Product {
    private int id;
    private String name;
    private int price;
    private int quantity;

    public Product() {
        this.id = 0;
        this.name = null;
        this.price = 0;
        this.quantity = 0;
    }

    public Product(int id, String name, int price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public int getPrice() {
        return price;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
