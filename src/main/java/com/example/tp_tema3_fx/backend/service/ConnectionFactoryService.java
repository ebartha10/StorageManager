package com.example.tp_tema3_fx.backend.service;

import com.example.tp_tema3_fx.backend.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectionFactoryService {
    private final ConnectionFactory connectionFactory = new ConnectionFactory();

    /**
     * Closes connection to database.
     */
    public void closeConnection(){
        connectionFactory.closeConnection();
    }

    /**
     *
     * @return Connection to the database.
     */
    public Connection getConnetion(){
        return connectionFactory.getConnection();
    }

    /**
     * Closes the statement.
     * @param statement
     */
    public void closeStatement(Statement statement){
        connectionFactory.close(statement);
    }

    /**
     * Closes the resultSet.
     * @param resultSet
     */
    public void closeResultSet(ResultSet resultSet){
        connectionFactory.close(resultSet);
    }
}
