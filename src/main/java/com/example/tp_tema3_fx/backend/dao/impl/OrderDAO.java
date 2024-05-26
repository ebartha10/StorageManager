package com.example.tp_tema3_fx.backend.dao.impl;

import com.example.tp_tema3_fx.backend.dao.AbstractDAO;
import com.example.tp_tema3_fx.backend.database.impl.QueryOrder;
import com.example.tp_tema3_fx.backend.dto.OrderEntryDTO;
import com.example.tp_tema3_fx.backend.model.Client;
import com.example.tp_tema3_fx.backend.model.Order;
import com.example.tp_tema3_fx.backend.model.Product;
import com.example.tp_tema3_fx.backend.service.ClientDaoService;
import com.example.tp_tema3_fx.backend.service.OrderDaoService;
import com.example.tp_tema3_fx.backend.service.ProductDaoService;
import com.example.tp_tema3_fx.backend.singleton.ClientDaoServiceSingleton;
import com.example.tp_tema3_fx.backend.singleton.ProductDaoServiceSingleton;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class OrderDAO extends AbstractDAO<Order> {
    private final QueryOrder queryService = new QueryOrder();
    private final ClientDaoService clientDaoService = ClientDaoServiceSingleton.getClientDaoService();
    private final ProductDaoService productDaoService = ProductDaoServiceSingleton.getProductDaoService();
    @SuppressWarnings("unchecked")
    private final Class<?> orderEntryDTOClass = OrderDaoService.ORDER_ENTRY_DTO_CLASS;
    @Override
    public int insert(Order givenObject) {
        Connection connection = connectionFactoryService.getConnetion();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String insertString = queryService.getInsert();
        String insertDetailsString = queryService.getInsertDetails();
        String insertBillString = queryService.getInsertBill();
        int newOrderId = -1;
        try{
            statement = connection.prepareStatement(insertString, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, givenObject.getClient().getId());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if(resultSet.next()){
                newOrderId = resultSet.getInt(1);
            }
            connectionFactoryService.closeResultSet(resultSet);
            connectionFactoryService.closeStatement(statement);

            for(Product product : givenObject.getProducts().keySet()){
                System.out.println(product);
                statement = connection.prepareStatement(insertDetailsString, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, newOrderId);
                statement.setInt(2, product.getId());
                statement.setInt(3, givenObject.getProducts().get(product));
                statement.executeUpdate();
                connectionFactoryService.closeStatement(statement);
            }
            connectionFactoryService.closeResultSet(resultSet);

            statement = connection.prepareStatement(insertBillString, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, newOrderId);
            statement.setString(2, LocalDateTime.now().toString());
            statement.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            connectionFactoryService.closeResultSet(resultSet);
            connectionFactoryService.closeStatement(statement);
        }
        return -1;
    }
    public List<Order> findByName(String givenName){
        Connection connection = connectionFactoryService.getConnetion();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = queryService.findByName();
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, givenName);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, getClass().getName() + "DAO:findByName " + e.getMessage());
        } finally {
            connectionFactoryService.closeResultSet(resultSet);
            connectionFactoryService.closeStatement(statement);
        }
        return null;
    }
    private OrderEntryDTO createObjectFromResultSet(ResultSet resultSet) throws SQLException{
        try {
            Constructor<?> constructor = orderEntryDTOClass.getDeclaredConstructor();                                         // GETS CONSTRUCTOR OF OBJECT
            OrderEntryDTO instance = (OrderEntryDTO) constructor.newInstance();                                                             // CREATES A NEW INSTANCE OF IT
            for(Field instanceField : orderEntryDTOClass.getDeclaredFields()){// FOR EACH FIELD IN THE OBJECT
                if(instanceField.getName().equals("ORDER_ENTRY_DTO_CLASS")){
                    continue;
                }
                instanceField.setAccessible(true);
                Object value = resultSet.getObject(instanceField.getName());                                    // GET THE VALUE FROM THE QUERY RES
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(instanceField.getName(), orderEntryDTOClass);  // FINDS THE GETTER AND SETTER
                propertyDescriptor.getWriteMethod().invoke(instance, value);                                    // CALLS SETTER TO SET THE FIELD
            }
            return instance;                                                                                    // RETURNS THE NEW OBJECT
        }
        catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException |
               IntrospectionException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    protected List<Order> createObjects(ResultSet resultSet) {
        Map<Integer, Order> orderMap = new HashMap<>();
        List<Order> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                OrderEntryDTO obj = createObjectFromResultSet(resultSet);
                int orderId = obj.getId();
                orderMap.putIfAbsent(orderId, new Order(orderId, clientDaoService.findById(obj.getClient_id())));
                Product product = productDaoService.findById(obj.getProduct_id());
                orderMap.get(orderId).getProducts().put(product, obj.getQuantity());
            }

            list.addAll(orderMap.values());
        }
        catch (SQLException e){
            LOGGER.log(Level.WARNING, "Error while creating objects from the resultSet.");
        }
        return list;
    }
}
