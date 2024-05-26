package com.example.tp_tema3_fx.backend.dto;

import java.lang.reflect.ParameterizedType;

/**
 * Object used as data transfer for the Order queries.
 */
public class OrderEntryDTO {
    private int id;
    private int client_id;
    private int product_id;
    private int quantity;
    public final Class<?> ORDER_ENTRY_DTO_CLASS;

    @SuppressWarnings("unchecked")
    public OrderEntryDTO() {
        this.ORDER_ENTRY_DTO_CLASS =  getClass();
    }
    @SuppressWarnings("unchecked")
    public OrderEntryDTO(int id, int client_id, int product_id, int quantity) {
        this.ORDER_ENTRY_DTO_CLASS =  getClass();
        this.id = id;
        this.client_id = client_id;
        this.product_id = product_id;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
