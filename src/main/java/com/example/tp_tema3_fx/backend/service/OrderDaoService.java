package com.example.tp_tema3_fx.backend.service;

import com.example.tp_tema3_fx.backend.dao.impl.OrderDAO;
import com.example.tp_tema3_fx.backend.dto.OrderEntryDTO;
import com.example.tp_tema3_fx.backend.model.Order;

import java.util.List;

public class OrderDaoService {
    private static final OrderEntryDTO orderEntryDTO = new OrderEntryDTO();
    public static final Class<?> ORDER_ENTRY_DTO_CLASS = orderEntryDTO.ORDER_ENTRY_DTO_CLASS;
    private final OrderDAO orderDAO = new OrderDAO();
    public int insert(Order order){
        return orderDAO.insert(order);
    }
    public Order findById(int givenId){
        return orderDAO.findById(givenId);
    }
    public List<Order> findAll(){
        return orderDAO.findAll();
    }
    public int update(Order givenObj){
        return orderDAO.update(givenObj);
    }
    public int delete(int givenId){
        return orderDAO.delete(givenId);
    }
    public List<Order> findByName(String givenName){
        return orderDAO.findByName(givenName);
    }
}
