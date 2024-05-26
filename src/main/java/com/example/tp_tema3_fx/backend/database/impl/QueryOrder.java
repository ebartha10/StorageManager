package com.example.tp_tema3_fx.backend.database.impl;

import com.example.tp_tema3_fx.backend.model.Order;

public class QueryOrder extends AbstractQuery<Order> {
    @Override
    public String getFindById() {
        return "SELECT Orders.id, Orders.client_id, order_details.product_id, order_details.quantity FROM Orders JOIN order_details ON Orders.id = order_details.order_id WHERE Orders.id = ?";

    }

    @Override
    public String getFindAll() {
        return "SELECT Orders.id, Orders.client_id, order_details.product_id, order_details.quantity FROM Orders JOIN order_details ON Orders.id = order_details.order_id";

    }

    @Override
    public String findByName() {
        return "SELECT Orders.id, Orders.client_id, order_details.product_id, order_details.quantity FROM Orders JOIN order_details ON Orders.id = order_details.order_id JOIN storage.clients on clients.id = Orders.client_id where clients.name = ?";

    }

    @Override
    public String getInsert() {
        return "INSERT INTO Orders (client_id) VALUES (?);";
    }
    public String getInsertDetails(){
        return "INSERT INTO Order_details (order_id, product_id, quantity) VALUES (?, ?, ?);";

    }
    @Override
    public String getUpdate() {
        return null;
    }

    @Override
    public String getDelete() {
        return "DELETE Orders, order_details FROM Orders JOIN order_details ON Orders.id = order_details.order_id WHERE Orders.id = ?";

    }
    public String getInsertBill(){
        return "INSERT INTO bills (order_id, date) VALUES (?,?);";

    }
}
