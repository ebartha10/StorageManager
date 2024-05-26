package com.example.tp_tema3_fx.backend.dao;

import com.example.tp_tema3_fx.backend.database.impl.AbstractQuery;
import com.example.tp_tema3_fx.backend.database.impl.QueryClient;
import com.example.tp_tema3_fx.backend.database.impl.QueryOrder;
import com.example.tp_tema3_fx.backend.database.impl.QueryProduct;
import com.example.tp_tema3_fx.backend.service.ConnectionFactoryService;
import com.example.tp_tema3_fx.backend.singleton.ConnectionFactorySingleton;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    protected final ConnectionFactoryService connectionFactoryService = ConnectionFactorySingleton.getConnectionFactoryService();
    protected final AbstractQuery queryService;

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        switch (type.getSimpleName()) {
            case "Product" -> queryService = new QueryProduct();
            case "Client" -> queryService = new QueryClient();
            case "Order" -> queryService = new QueryOrder();
            default -> queryService = null;
        }

    }

    /**
     * Searches the database for all the entries of type T.
     * @return List of Objects of type T.
     */
    public List<T> findAll() {
        Connection connection = connectionFactoryService.getConnetion();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = queryService.getFindAll();
        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects((resultSet));
        }
        catch (SQLException e){
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        }
        finally {
            connectionFactoryService.closeStatement(statement);
            connectionFactoryService.closeResultSet(resultSet);
        }
        return null;
    }

    /**
     * Searches the database for an object with the given id.
     * @param id of the entry in the database.
     * @return Object of type T or null if object is not existent.
     */
    public T findById(int id) {
        Connection connection = connectionFactoryService.getConnetion();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = queryService.getFindById();
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            connectionFactoryService.closeResultSet(resultSet);
            connectionFactoryService.closeStatement(statement);
        }
        return null;
    }

    /**
     * Inserts the given object into the table if it doesn't already exist.
     * @param givenObject Object of type T
     * @return the id of the new inserted item or -1 if insertion was not successful.
     */
    public int insert(T givenObject) {
        Connection connection = connectionFactoryService.getConnetion();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String insertString = queryService.getInsert();
        try{
            statement = connection.prepareStatement(insertString, Statement.RETURN_GENERATED_KEYS);
            int paramIndex = 1;
            for(Field instanceField : type.getDeclaredFields()){
                if(instanceField.getName().equals("id")){
                    continue;
                }
                instanceField.setAccessible(true);
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(instanceField.getName(), type);
                Object value = propertyDescriptor.getReadMethod().invoke(givenObject);
                statement.setObject(paramIndex++, value);
            }
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
        }
        catch (SQLException | IntrospectionException | IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
        }
        finally {
            connectionFactoryService.closeResultSet(resultSet);
            connectionFactoryService.closeStatement(statement);
        }
        return -1;
    }

    /**
     * Updates the values from the database for the given object and returns the number of the rows affected.
     * @param givenObject object to be updated
     * @return numbers of affected rows.
     */
    public int update(T givenObject) {
        Connection connection = connectionFactoryService.getConnetion();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String insertString = queryService.getUpdate();
        try{
            statement = connection.prepareStatement(insertString, Statement.RETURN_GENERATED_KEYS);
            int paramIndex = 1;
            Object objectId = -1;

            for(Field instanceField : type.getDeclaredFields()){
                instanceField.setAccessible(true);
                if(instanceField.getName().equals("id")){
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(instanceField.getName(), type);
                    objectId = propertyDescriptor.getReadMethod().invoke(givenObject);
                    continue;
                }
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(instanceField.getName(), type);
                Object value = propertyDescriptor.getReadMethod().invoke(givenObject);
                statement.setObject(paramIndex++, value);
            }
            statement.setObject(paramIndex, objectId);

            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
        }
        catch (SQLException | IntrospectionException | IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
        }
        finally {
            connectionFactoryService.closeStatement(statement);
            connectionFactoryService.closeResultSet(resultSet);
        }
        return -1;
    }

    /**
     * Deletes rows from the database that have the given id.
     * @param givenId id of the rows to be deleted
     * @return number of affected rows.
     */
    public int delete(int givenId){
        Connection connection = connectionFactoryService.getConnetion();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String insertString = queryService.getDelete();
        try{
            statement = connection.prepareStatement(insertString, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, givenId);
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            connectionFactoryService.closeStatement(statement);
            connectionFactoryService.closeResultSet(resultSet);
        }
        return -1;
    }

    /**
     * Creates an object from curret resultSet entity.
     * @param resultSet Result of SQL query.
     * @return Java object corresponding to values in the set.
     * @throws SQLException If column names do not match with java object fields.
     */
    private T createObjectFromResultSet(ResultSet resultSet) throws SQLException{
        try {
            Constructor<T> constructor = type.getDeclaredConstructor();                                         // GETS CONSTRUCTOR OF OBJECT
            T instance = constructor.newInstance();                                                             // CREATES A NEW INSTANCE OF IT
            for(Field instanceField : type.getDeclaredFields()){                                                // FOR EACH FIELD IN THE OBJECT
                instanceField.setAccessible(true);
                Object value = resultSet.getObject(instanceField.getName());                                    // GET THE VALUE FROM THE QUERY RES
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(instanceField.getName(), type);  // FINDS THE GETTER AND SETTER
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

    /**
     * Creates a complete object list from query result.
     * @param resultSet Whole result set of query
     * @return List of corresponding objects.
     */
    protected List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                T obj = createObjectFromResultSet(resultSet);
                list.add(obj);
            }
        }
        catch (SQLException e){
            LOGGER.log(Level.WARNING, "Error while creating objects from the resultSet.");
        }
        return list;
    }

}
