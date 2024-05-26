package com.example.tp_tema3_fx.backend.singleton;

import com.example.tp_tema3_fx.backend.service.OrderDaoService;

public class OrderDaoServiceSingleton {
    private final static OrderDaoService orderDaoService = new OrderDaoService();
    public static OrderDaoService getOrderDaoService(){
        return orderDaoService;
    }
}
