package com.example.tp_tema3_fx.backend.service;

import com.example.tp_tema3_fx.backend.dao.impl.ClientDAO;
import com.example.tp_tema3_fx.backend.dao.impl.OrderDAO;
import com.example.tp_tema3_fx.backend.dto.OrderEntryDTO;
import com.example.tp_tema3_fx.backend.model.Client;

import java.util.List;

public class ClientDaoService {
    private final ClientDAO clientDAO = new ClientDAO();
    public List<Client> findAll(){
        return clientDAO.findAll();
    }
    public Client findById(int givenId){
        return clientDAO.findById(givenId);
    }
    public List<Client> findByName(String givenName){
        return clientDAO.findByName(givenName);
    }
    public int update(Client givenProduct){
        return clientDAO.update(givenProduct);
    }
    public int insert(Client givenProduct){
        return clientDAO.insert(givenProduct);
    }
    public int delete(int givenId){
        return clientDAO.delete(givenId);
    }
}
