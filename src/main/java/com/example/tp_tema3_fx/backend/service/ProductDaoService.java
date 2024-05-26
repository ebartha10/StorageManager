package com.example.tp_tema3_fx.backend.service;

import com.example.tp_tema3_fx.backend.dao.AbstractDAO;
import com.example.tp_tema3_fx.backend.dao.impl.ProductDAO;
import com.example.tp_tema3_fx.backend.model.Product;

import java.util.List;

public class ProductDaoService {
    private final ProductDAO productDAO = new ProductDAO();
    public List<Product> findAll(){
        return productDAO.findAll();
    }
    public Product findById(int givenId){
        return productDAO.findById(givenId);
    }
    public List<Product> findByName(String givenName){
        return productDAO.findByName(givenName);
    }
    public int update(Product givenProduct){
        return productDAO.update(givenProduct);
    }
    public int insert(Product givenProduct){
        return productDAO.insert(givenProduct);
    }
    public int delete(int givenId){
        return productDAO.delete(givenId);
    }
}
