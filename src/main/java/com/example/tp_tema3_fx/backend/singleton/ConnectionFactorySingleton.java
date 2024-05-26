package com.example.tp_tema3_fx.backend.singleton;

import com.example.tp_tema3_fx.backend.service.ConnectionFactoryService;
public class ConnectionFactorySingleton {
    private static final ConnectionFactoryService connectionFactoryService = new ConnectionFactoryService();

    /**
     * Returns the unique instance of the ConnectionFactory.
     * @return ConnectionFactoryService object.
     */
    public static ConnectionFactoryService getConnectionFactoryService(){
        return connectionFactoryService;
    }
}
