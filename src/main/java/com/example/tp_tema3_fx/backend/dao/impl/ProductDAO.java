package com.example.tp_tema3_fx.backend.dao.impl;

import com.example.tp_tema3_fx.backend.dao.AbstractDAO;
import com.example.tp_tema3_fx.backend.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

public class ProductDAO extends AbstractDAO<Product> {
    /**
     * Returns a list of products with the given name or null if there are no matches.
     * @param givenName name to be searched for
     * @return list of Product Objects.
     */
    public List<Product> findByName(String givenName){
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
}
