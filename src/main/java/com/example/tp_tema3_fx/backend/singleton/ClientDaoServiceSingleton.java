package com.example.tp_tema3_fx.backend.singleton;

import com.example.tp_tema3_fx.backend.service.ClientDaoService;

public class ClientDaoServiceSingleton {
    private static final ClientDaoService clientDaoService = new ClientDaoService();
    public static ClientDaoService getClientDaoService(){
        return clientDaoService;
    }
}
